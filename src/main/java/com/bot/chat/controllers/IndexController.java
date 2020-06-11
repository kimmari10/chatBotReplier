package com.bot.chat.controllers;

import com.bot.chat.dto.ResponseSummaryDto;
import com.bot.chat.dto.command.ResponseCommandDto;
import com.bot.chat.dto.message.ResponseMessageDto;
import com.bot.chat.service.InfoService;
import com.bot.chat.service.MessageService;
import com.bot.chat.service.ReplyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@AllArgsConstructor
public class IndexController {

    private final ReplyService replyService;
    private final MessageService messageService;
    private final InfoService infoService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String summary(Model model) {
        ResponseSummaryDto summary = infoService.getSummary();

        model.addAttribute("summary", summary);
        return "dashboard";
    }

    @GetMapping("/command/list")
    public String commandList(Model model) {
        List<ResponseCommandDto> list = replyService.getCommandList();

        model.addAttribute("commands", list);
        return "command/list";
    }


    @GetMapping("/command/save")
    public String commandSave() {
        return "command/save";
    }

    @GetMapping("/message/list")
    public String messageList(Model model) {
        List<ResponseMessageDto> list = messageService.getMessageList();

        model.addAttribute("messages", list);
        return "message/list";
    }

    @GetMapping("/message/list/{sender}")
    public String messageList(Model model, @PathVariable String sender) {
        List<ResponseMessageDto> list = messageService.getMessageListBySender(sender);

        model.addAttribute("messages", list);
        return "message/list";
    }

}
