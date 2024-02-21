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
        // instead of returning the button text directly here,
        // could simply return currentState()
        return currentState.buttonText();
    }

    @GetMapping("/next-state")
    public String currentState() {
        // language=html
        return """
                <p id="three-way-state" hx-swap-oob="innerHTML">%s</p>
                """.formatted(currentState.buttonText());
    }

    @PostMapping("/next-state-multiple-targets")
    public String nextStateMultipleTargets() {
        currentState = currentState.next();
        return currentStateMultipleTargets();
    }

    @GetMapping("/next-state-multiple-targets")
    // language=html
    public String currentStateMultipleTargets() {
        String optionalButton = """
                <swap-container hx-swap-oob="beforeend" id="three-way-toggler-multiple-targets">
                <button class="mt-2 bg-blue-500 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded"
                        id="click-me-button">
                       Move On to Next Item</button>
                </swap-container>
                """;
        String deleteButton = """
                <delete-element hx-swap-oob="delete" id="click-me-button"/>
                """;
        return """
                %s
                <span id="three-way-current-state"
                      hx-swap-oob="innerHTML">%s</span>
                %s
                """.formatted(currentState.toString(),
                              currentState.buttonText(),
                              currentState == ThreeWayState.DONE ? optionalButton : deleteButton);
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
