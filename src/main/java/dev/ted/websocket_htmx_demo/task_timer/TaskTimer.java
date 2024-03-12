package dev.ted.websocket_htmx_demo.task_timer;

class TaskTimer {

    private TaskTimerState currentState = TaskTimerState.WAITING_TO_START;

    TaskTimerState currentState() {
        return currentState;
    }

    public void start() {
        currentState = TaskTimerState.STARTED;
    }

    public void pause() {
        currentState = TaskTimerState.PAUSED;
    }

    public void resume() {
        currentState = TaskTimerState.STARTED;
    }

    public void complete() {
        currentState = TaskTimerState.COMPLETED;
    }

    enum TaskTimerState {
        WAITING_TO_START,
        STARTED,
        PAUSED,
        COMPLETED
    }
}
