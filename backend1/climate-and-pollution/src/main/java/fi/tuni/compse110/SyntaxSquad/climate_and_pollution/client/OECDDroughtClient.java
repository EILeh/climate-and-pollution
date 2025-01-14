package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(
    name = "OECDDroughtClient",
    url = "https://sdmx.oecd.org"
)

public interface OECDDroughtClient {

    @GetMapping("/public/rest/data/OECD.ENV.EPI,DSD_ECH@EXT_DROUGHT,1.1/{cca3}.A.ED_CROP_ANOM.....")
    String getDroughtData(
        @PathVariable String cca3,
        @RequestParam("startPeriod") String startPeriod,
        @RequestParam("endPeriod") String endPeriod,
        @RequestParam("format") String format
    );
}
