package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "OECDMortalityClient",
    url = "https://sdmx.oecd.org"
)

public interface OECDMortalityClient {

    @GetMapping("/public/rest/data/OECD.ENV.EPI,DSD_EXP_MORSC@DF_EXP_MORSC,1.0/{cca3}.A.DALY.10P3HB.PM_2_5_OUT._T._T")
    String getMortalityData(
        @PathVariable String cca3,
        @RequestParam("startPeriod") String startPeriod,
        @RequestParam("endPeriod") String endPeriod,
        @RequestParam("format") String format
    );
}
