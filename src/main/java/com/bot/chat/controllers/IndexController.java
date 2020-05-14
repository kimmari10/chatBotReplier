package com.bot.chat.controllers;

import com.bot.chat.dto.ResponseCommandDto;
import com.bot.chat.service.CommandService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class IndexController {

    private final CommandService commandService;

    @GetMapping("/")
    public String getCommandList(Model model) {
        List<ResponseCommandDto> list = commandService.getCommandList();

        model.addAttribute("commands", list);
        return "index";
    }

    @GetMapping("/save")
    public String save() {

        return "save";
    }
}
