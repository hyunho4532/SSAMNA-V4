package com.app.domain.model.enum

sealed class ButtonType {

    sealed class PermissionStatus: ButtonType() {
        data object USERCLICK: PermissionStatus()
        data object USERCANCEL: PermissionStatus()
        data object PRIVACYCLICK: PermissionStatus()
        data object PRIVACYCANCEL: PermissionStatus()
    }

    sealed class EventStatus: ButtonType() {
        data object ROUTE: EventStatus()
        data object DARKTHEME: EventStatus()
    }

    sealed class RunningStatus: ButtonType() {
        data object FINISH : RunningStatus()
        data object OPEN: RunningStatus()
        sealed class InsertStatus: ButtonType() {
            data object RUNNING: InsertStatus()
            data object CHALLENGE: InsertStatus()
        }
        sealed class DeleteStatus: ButtonType() {
            data object RUNNING: DeleteStatus()
            data object CHALLENGE: DeleteStatus()
        }
    }

    sealed class HistoryStatus: ButtonType() {
        data object OPEN: RunningStatus()
    }

    sealed class MarkerStatus: ButtonType() {
        data object FINISH: MarkerStatus()
    }

    sealed class CrewStatus: ButtonType() {
        data object INSERT: CrewStatus()
        data object Delete: CrewStatus()
    }
}