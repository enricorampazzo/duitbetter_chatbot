package dev.ted.websocket_htmx_demo.task_timer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TaskTimerHtmxController {

    private final TaskTimer taskTimer = new TaskTimer();

    @GetMapping("/htmx-options/task-timer")
    public String showPage() {
        return "task-timer-option-htmx";
    }

    @GetMapping("/htmx-options/task-timer-state")
    @ResponseBody
    public String taskTimerState() {
        return """
               <swap-container id="current-state" hx-swap-oob="innerHTML">
               %s
               </swap-container>
               <swap-container id="button-div" hx-swap-oob="innerHTML">
                   <button class="mt-2 bg-blue-500 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded"
                           hx-post="/htmx-options/task-timer/start">
                       Start
                   </button>
                   <button class="mt-2 bg-blue-500 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded"
                           hx-post="/htmx-options/task-timer/pause">
                       Pause
                   </button>
                   <button class="mt-2 bg-blue-500 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded"
                           hx-post="/htmx-options/task-timer/resume">
                       Resume
                   </button>
                   <button class="mt-2 bg-blue-500 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded"
                           hx-post="/htmx-options/task-timer/complete">
                       Complete Task
                   </button>
               </swap-container>
               """.formatted(taskTimer.currentState().toString());
    }

    @PostMapping("/htmx-options/task-timer/start")
    @ResponseBody
    public String start() {
        taskTimer.start();
        return taskTimerState();
    }

    @PostMapping("/htmx-options/task-timer/pause")
    @ResponseBody
    public String pause() {
        taskTimer.pause();
        return taskTimerState();
    }

    @PostMapping("/htmx-options/task-timer/resume")
    @ResponseBody
    public String resume() {
        taskTimer.resume();
        return taskTimerState();
    }

    @PostMapping("/htmx-options/task-timer/complete")
    @ResponseBody
    public String complete() {
        taskTimer.complete();
        return taskTimerState();
    }
}

//        List<Button> buttons = switch (taskTimer.currentState()) {
//            case WAITING_TO_START -> List.of(
//                            new Button("Start", "/htmx-options/task-timer/start"));
//            case STARTED -> List.of(
//                    new Button("Pause", "/htmx-options/task-timer/pause"),
//                    new Button("Complete Task", "/htmx-options/task-timer/complete"));
//            case PAUSED -> List.of(
//                    new Button("Resume", "/htmx-options/task-timer/resume"),
//                    new Button("Complete Task", "/htmx-options/task-timer/complete"));
//            case COMPLETED -> emptyList();
//        };
//        model.addAttribute("buttons", buttons);
