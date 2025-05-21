package com.app.domain.model.enum

sealed class CardType {
    sealed class ActivateStatus: CardType() {
        data object Running: ActivateStatus()
        data object Activity: ActivateStatus()
        data object Form: ActivateStatus()
    }
}

sealed class ProfileStatusType  {
    data object Activate: ProfileStatusType()
    data object Kcal: ProfileStatusType()
    data object Km: ProfileStatusType()
}