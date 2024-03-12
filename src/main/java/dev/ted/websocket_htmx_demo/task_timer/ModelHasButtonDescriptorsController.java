package dev.ted.websocket_htmx_demo.task_timer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import static java.util.Collections.emptyList;

@Controller
public class ModelHasButtonDescriptorsController {

    private final TaskTimer taskTimer = new TaskTimer();

    @GetMapping("/model-buttons/task-timer")
    public String showPage(Model model) {
        model.addAttribute("state", taskTimer.currentState().toString());

        List<Button> buttons = switch (taskTimer.currentState()) {
            case WAITING_TO_START -> List.of(
                            new Button("Start", "/model-buttons/task-timer/start"));
            case STARTED -> List.of(
                    new Button("Pause", "/model-buttons/task-timer/pause"),
                    new Button("Complete Task", "/model-buttons/task-timer/complete"));
            case PAUSED -> List.of(
                    new Button("Resume", "/model-buttons/task-timer/resume"),
                    new Button("Complete Task", "/model-buttons/task-timer/complete"));
            case COMPLETED -> emptyList();
        };
        model.addAttribute("buttons", buttons);

        return "task-timer-option-model-button-descriptors";
    }

    @PostMapping("/model-buttons/task-timer/start")
    public String start() {
        taskTimer.start();
        return "redirect:/model-buttons/task-timer";
    }

    @PostMapping("/model-buttons/task-timer/pause")
    public String pause() {
        taskTimer.pause();
        return "redirect:/model-buttons/task-timer";
    }

    @PostMapping("/model-buttons/task-timer/resume")
    public String resume() {
        taskTimer.resume();
        return "redirect:/model-buttons/task-timer";
    }

    @PostMapping("/model-buttons/task-timer/complete")
    public String complete() {
        taskTimer.complete();
        return "redirect:/model-buttons/task-timer";
    }
}

record Button(String label, String actionUrl) {}
