package dev.ted.websocket_htmx_demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HtmxPostController {

    private boolean active;
    private ThreeWayState currentState = ThreeWayState.WAITING;

    @PostMapping("/post-constant")
    public String postReturnsConstant() {
        return "Deactivate";
    }

    @PostMapping("/post-toggle-active")
    public String postToggleActive() {
        active = !active;
        return active ? "Deactivate" : "Activate";
    }

    @PostMapping("/next-state")
    public String nextState() {
        currentState = currentState.next();
        return currentState.buttonText();
    }

    @GetMapping("/next-state")
    public String currentState() {
        // language=html
        return """
               <p id="three-way-state" hx-swap-oob="innerHTML">%s</p>
               """.formatted(currentState.buttonText());
    }


    private enum ThreeWayState {
        WAITING("Click to Run"),
        RUNNING("Click to End"),
        DONE("Click to Reset");

        private final String buttonText;

        ThreeWayState(String buttonText) {
            this.buttonText = buttonText;
        }

        public ThreeWayState next() {
            return switch (this) {
                case WAITING -> RUNNING;
                case RUNNING -> DONE;
                case DONE -> WAITING;
            };
        }

        public String buttonText() {
            return buttonText;
        }
    }
}
