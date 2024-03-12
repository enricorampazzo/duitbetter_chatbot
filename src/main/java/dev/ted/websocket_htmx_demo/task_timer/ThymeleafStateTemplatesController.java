package dev.ted.websocket_htmx_demo.task_timer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ThymeleafStateTemplatesController {

    private final TaskTimer taskTimer = new TaskTimer();

    @GetMapping("/thymeleaf-state-templates/task-timer")
    public String showPage(Model model) {
        model.addAttribute("state", taskTimer.currentState().toString());
        return switch (taskTimer.currentState()) {
            case WAITING_TO_START -> "task-timer-controller-template-waiting-to-start";
            case STARTED -> "task-timer-controller-template-started";
            case PAUSED -> "task-timer-controller-template-paused";
            case COMPLETED -> "task-timer-controller-template-completed";
        };
    }

    @PostMapping("/thymeleaf-state-templates/task-timer/start")
    public String start() {
        taskTimer.start();
        return "redirect:/thymeleaf-state-templates/task-timer";
    }

    @PostMapping("/thymeleaf-state-templates/task-timer/pause")
    public String pause() {
        taskTimer.pause();
        return "redirect:/thymeleaf-state-templates/task-timer";
    }

    @PostMapping("/thymeleaf-state-templates/task-timer/resume")
    public String resume() {
        taskTimer.resume();
        return "redirect:/thymeleaf-state-templates/task-timer";
    }

    @PostMapping("/thymeleaf-state-templates/task-timer/complete")
    public String complete() {
        taskTimer.complete();
        return "redirect:/thymeleaf-state-templates/task-timer";
    }
}

