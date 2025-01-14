package fi.tuni.compse110.SyntaxSquad.climate_and_pollution.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.model.OECDData;
import fi.tuni.compse110.SyntaxSquad.climate_and_pollution.service.OECDService;


@RestController
@RequestMapping("/api")
public class EnvironmentalDataController {

    @Autowired
    private OECDService oecdService;
    
    @GetMapping("/mortality")
    public List<OECDData> getMortalityData(@RequestParam String country) throws Exception {
        System.out.println("Fetching OECD Mortality data for country:"+ country);
        return oecdService.getMortalityData(country);
    }
    
    @GetMapping("/xtemp")
    public List<OECDData> getXTempData(@RequestParam String country) throws Exception {
        System.out.println("Fetching OECD xTemp data for country:"+ country);

        return oecdService.getXTempData(country);
    }

    @GetMapping("/drought")
    public List<OECDData> getDroughtData(@RequestParam String country) throws Exception {
        System.out.println("Fetching indexed OECD drought data for country:"+ country);

        return oecdService.getDroughtData(country);
    }

}
