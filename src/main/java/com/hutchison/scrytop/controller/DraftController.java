package com.hutchison.scrytop.controller;

import com.hutchison.scrytop.model.draft.Draft;
import com.hutchison.scrytop.service.DraftService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/draft")
public class DraftController {

    DraftService draftService;

    @Autowired
    public DraftController(DraftService draftService) {
        this.draftService = draftService;
    }

    @GetMapping("/wotc/{si1}/{si2}/{si3}/{playerCount}")
    public ResponseEntity<Draft> getDraft(@PathVariable String si1,
                                               @PathVariable String si2,
                                               @PathVariable String si3,
                                               @PathVariable Integer playerCount) {
        return ResponseEntity.ok(draftService.getDraft(Arrays.asList(si1, si2, si3), playerCount));
    }

    @GetMapping("/cube/{cubeIdentifier}/{playerCount}")
    public ResponseEntity<Draft> getCubeDraft(@PathVariable String cubeIdentifier,
                                               @PathVariable Integer playerCount) {
        return ResponseEntity.ok(draftService.getCubeDraft(cubeIdentifier, playerCount));
    }
}
