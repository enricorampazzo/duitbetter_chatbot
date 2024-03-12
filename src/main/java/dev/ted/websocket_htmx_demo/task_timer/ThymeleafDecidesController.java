package dev.ted.websocket_htmx_demo.task_timer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ThymeleafDecidesController {

    private final TaskTimer taskTimer = new TaskTimer();

    @GetMapping("/thymeleaf/task-timer")
    public String showPage(Model model) {
        model.addAttribute("state", taskTimer.currentState().toString());
        return "task-timer-option-thymeleaf-decides";
    }

    @PostMapping("/thymeleaf/task-timer/start")
    public String start() {
        taskTimer.start();
        return "redirect:/thymeleaf/task-timer";
    }

    @PostMapping("/thymeleaf/task-timer/pause")
    public String pause() {
        taskTimer.pause();
        return "redirect:/thymeleaf/task-timer";
    }

    @PostMapping("/thymeleaf/task-timer/resume")
    public String resume() {
        taskTimer.resume();
        return "redirect:/thymeleaf/task-timer";
    }

    @PostMapping("/thymeleaf/task-timer/complete")
    public String complete() {
        taskTimer.complete();
        return "redirect:/thymeleaf/task-timer";
    }
}

