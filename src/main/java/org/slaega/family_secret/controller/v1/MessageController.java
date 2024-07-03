package org.slaega.family_secret.controller.v1;

import org.slaega.family_secret.dto.message.MessageDto;
import org.slaega.family_secret.dto.message.RequestMessageDto;
import org.slaega.family_secret.service.IMessageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

import java.util.Optional;
import java.util.List;

import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("v1/messages")
@Data
public class MessageController {
    @Autowired
    private IMessageService messageService;

    @PostMapping
    public MessageDto create(@RequestBody @Valid RequestMessageDto requestMessageDto) {
       
        return this.messageService.create(requestMessageDto);

    }

    @GetMapping(":id")
    public Optional<MessageDto> findOne(@PathVariable(name = "id") String id) {
        return this.messageService.findOne(id);
    }

    @GetMapping
    public List<MessageDto> find() {
        return this.messageService.find();
    }

    @PutMapping(":id")
    public MessageDto update(@PathVariable(name = "id") String id,@RequestBody @Valid RequestMessageDto requestMessageDto) {
        return this.messageService.update(id,requestMessageDto);
    }

    @DeleteMapping(":id")
    public void delete(@PathVariable(name = "id") String id) {
        this.messageService.deleteById(id);
    }
}
