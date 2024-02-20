package dev.ted.websocket_htmx_demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@RestController
public class HtmxController {

    @GetMapping("/replaceh1")
    public String replaceH1(@RequestParam(value = "oob", defaultValue = "false") Boolean oob) {
        return """
                <h1 id="replaceh1" %s
                    class="font-bold text-sm text-gray-600 uppercase">Replaced by server response at %s</h1>"""
                .formatted(oob ? "hx-swap-oob=\"true\"" : "",
                        LocalTime.now());
    }

}
