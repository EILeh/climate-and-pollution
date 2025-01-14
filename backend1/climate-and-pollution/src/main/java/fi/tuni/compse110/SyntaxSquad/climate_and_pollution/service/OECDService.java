package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.service;

import java.io.StringReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import feign.FeignException;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.client.OECDDroughtClient;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.client.OECDExtremeTempClient;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.client.OECDMortalityClient;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.exception.OECDRateLimitExceededException;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.exception.OECDServiceException;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.OECDData;

@Service
public class OECDService {

    @Autowired
    private OECDMortalityClient mortalityClient;

    @Autowired
    private OECDExtremeTempClient xTempClient;

    @Autowired
    private OECDDroughtClient droughtClient;

    @Value("${openweather.api.key}")
    private String apiKey;

    /**
     * Fetch mortality data and parse it
     * @param cca3: 3 letter country code
     * @return List<OECDData>: List of data items with fetched info: year, cca3, value
     * @throws Exception
     */
    public List<OECDData> getMortalityData(String cca3) throws Exception {
        try {
            String mortData = mortalityClient.getMortalityData(cca3, "2000", "2030", "genericdata");

            DocumentBuilder builder = defineBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(mortData)));

            List<OECDData> results = extractAndProcessNodes(doc, cca3);
            return results;

        } catch (FeignException e) {
        if (e.status() == 429 || e.contentUTF8().contains("You have exceeded the number of requests for data")) {
            throw new OECDRateLimitExceededException("API rate limit exceeded (20 queries/hour)", e);
        }
        throw new OECDServiceException("Error while fetching Mortality data", e);
    }
    }

    /**
     * Fetch extreme temperature data and parse it
     * @param cca3: 3 letter country code
     * @return List<OECDData>: List of data items with fetched info: year, cca3, value
     * @throws Exception
     */
    public List<OECDData> getXTempData(String cca3) throws Exception {
        try {
            String xTempData = xTempClient.getExtremeTempData(cca3, "2000", "2030", "genericdata");

            DocumentBuilder builder = defineBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xTempData)));

            List<OECDData> results = extractAndProcessNodes(doc, cca3);
            return results;

        } catch (FeignException e) {
            if (e.status() == 429 || e.contentUTF8().contains("You have exceeded the number of requests for data")) {
                throw new OECDRateLimitExceededException("API rate limit exceeded (20 queries/hour)", e);
            }
            throw new OECDServiceException("Error while fetching extreme temperature data", e);
        }
    }

    /**
     * Fetch drought data, parse it, and INDEX the values. 
     * Base year is the first data point of the search, and base value is 100
     * @param cca3: 3 letter country code
     * @return List<OECDData>: List of data items with fetched info: year, cca3, INDEXED value
     * @throws Exception
     */

    public List<OECDData> getDroughtData(String cca3) throws Exception {
        try {
            String droughtData = droughtClient.getDroughtData(cca3, "2000", "2030", "genericdata");

            DocumentBuilder builder = defineBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(droughtData)));

            List<OECDData> results = extractAndProcessNodes(doc, cca3);

            // Calculating the indexes of the values and replacing them
            double index = 100; //setting the base value of the index. Change freely if some other scope is needed
            for(int i = 0; i<results.size(); i++){
                index += index*(results.get(i).getValue() / 100);
                results.get(i).setValue(index);
            }

            return results;

        } catch (FeignException e) {
            if (e.status() == 429 || e.contentUTF8().contains("You have exceeded the number of requests for data")) {
                throw new OECDRateLimitExceededException("API rate limit exceeded (20 queries/hour)", e);
            }
            throw new OECDServiceException("Error while fetching drought data", e);
        }
    }

    public DocumentBuilder defineBuilder() throws Exception {
        try{
            // Parse XML using DOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

            return factory.newDocumentBuilder();
        } catch(Exception e){
            throw new OECDServiceException("Error while defining builder for node processing", e);
        }

    }

    public List<OECDData> extractAndProcessNodes(Document doc, String cca3){
        // Extract `Obs` nodes
        NodeList obsNodes = doc.getElementsByTagNameNS("*", "Obs");

        // Process nodes using streams
        List<OECDData> results =
            IntStream.range(0, obsNodes.getLength())
                .mapToObj(i -> (Element) obsNodes.item(i))
                .map(obs -> {
                    try {
                        String year = Optional.ofNullable(obs.getElementsByTagNameNS("*", "ObsDimension")
                                            .item(0))
                            .map(el -> el.getAttributes().getNamedItem("value").getNodeValue())
                            .orElseThrow(() -> new OECDServiceException("Missing ObsDimension value"));

                        String value = Optional.ofNullable(obs.getElementsByTagNameNS("*", "ObsValue")
                                            .item(0))
                            .map(el -> el.getAttributes().getNamedItem("value").getNodeValue())
                            .orElseThrow(() -> new OECDServiceException("Missing ObsValue value"));

                        return new OECDData(cca3, Integer.valueOf(year), Double.valueOf(value));
                    } catch (Exception e) {
                        throw new RuntimeException("Error processing observation node", e);
                    }
                })
                .collect(Collectors.toList());

        // Sort the list by year
        results.sort((o1, o2) -> o1.getYear() - o2.getYear());
        return results;
    }
}
