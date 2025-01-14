package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(
    name = "OECDExtremeTempClient",
    url = "https://sdmx.oecd.org"

    )
public interface OECDExtremeTempClient {

    @GetMapping("/public/rest/data/OECD.ENV.EPI,DSD_ECH@EXT_TEMP,1.1/{cca3}.A.HD_POP_EXP..W_LT_2...")
    String getExtremeTempData(
        @PathVariable("cca3") String cca3,
        @RequestParam("startPeriod") String startPeriod,
        @RequestParam("endPeriod") String endPeriod,
        @RequestParam("format") String format
    );
}
