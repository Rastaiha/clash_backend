package clash.back.controller;

import clash.back.service.CivilizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/civilization")
public class CivilizationController {

    @Autowired
    CivilizationService civilizationService;
}
