package com.example.backend_lab.controller;

import com.example.backend_lab.model.Channel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChannelController {

    @GetMapping("/channels")
    public String Channels(Model model) {
        List<Channel> channels = new ArrayList<>();
        channels.add(new Channel("1", "김철수", "안녕하세요", 0, "N", "N"));
        channels.add(new Channel("2", "이영희", "회의 자료 보냈어요", 3, "Y", "N"));
        channels.add(new Channel("3", "박민수", "넵 확인했습니다", 120, "N", "N"));
        channels.add(new Channel("4", "탈퇴한 사용자", "이 메시지는 안 보여야 함", 0, "N", "Y"));

        model.addAttribute("channels", channels);
        model.addAttribute("loginEmpId","1001");

        return "channels";
    }
}
