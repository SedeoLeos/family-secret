package org.slaega.family_secret.controller.v1;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.slaega.family_secret.dto.discussion.RequestDiscussionDto;
import org.slaega.family_secret.mappers.DiscussionMapper;
import org.slaega.family_secret.dto.discussion.DiscussionDto;
import org.slaega.family_secret.service.DiscussionService;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("v1/discussions")
public class DiscussionController {
    @Autowired
    private DiscussionService discussionService;
   

    @PostMapping
    public DiscussionDto create(@RequestBody RequestDiscussionDto requestDiscussionDto) {
        return this.discussionService.create(requestDiscussionDto);
    }

    @GetMapping
    public List<DiscussionDto> findAll() {
        return this.discussionService.find();
    }

    

    @GetMapping(":id")
    public Optional<DiscussionDto> findOne(@PathVariable(name = "id") String id) {
        return this.discussionService.findById(id);
    }

    @PutMapping(":id")
    public DiscussionDto update(@PathVariable String id, @RequestBody RequestDiscussionDto requestDiscussionDto) {
        return this.discussionService.update(id, requestDiscussionDto);
    }

    @DeleteMapping(":id")
    public void remove(@PathVariable String id) {
        this.discussionService.deleteById(id);
    }
}
