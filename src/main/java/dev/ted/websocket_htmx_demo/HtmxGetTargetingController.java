package dev.ted.websocket_htmx_demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@RestController
public class HtmxGetTargetingController {

    @GetMapping("/replace-div")
    public String replaceDiv() {
        // language=html
        return """
                <div id="replaced-div">
                    Replaced DIV content from server, generated at %s...
                </div>"""
                .formatted(LocalTime.now());
    }

    @GetMapping("/replace-div-target-of-oob")
    public String replaceDivWithOobSwap() {
        // language=html
        return """
                <div id="target-of-oob" hx-swap-oob="true">
                    Replaced DIV content from server, generated at %s...
                </div>
                leftover text"""
                .formatted(LocalTime.now());
    }

    @GetMapping("/replace-div-target-of-oob-swap-none")
    public String replaceDivWithOobSwapAndSwapNoneOnClient() {
        return """
                <div id="target-of-oob-swap-none" hx-swap-oob="true">
                    Replaced DIV content from server, generated at %s...
                </div>
                ignored text"""
                .formatted(LocalTime.now());
    }


    @GetMapping("/replace-multiple-targets-of-oob-swap")
    public String replaceMultipleTargetsOfOobSwap() {
        return """
                <h2 id="another-target-h2"
                    hx-swap-oob="true"
                    class="font-bold text-xl text-gray-500 bg-white mt-2">
                    H2 Has Been Replaced
                </h2>
                <div id="multiple-targets-of-oob-swap"
                     class="border-green-300 bg-yellow-50 p-2"
                     hx-swap-oob="true">
                    Replaced DIV content target by server, generated at %s...
                </div>"""
                .formatted(LocalTime.now());
    }

}
