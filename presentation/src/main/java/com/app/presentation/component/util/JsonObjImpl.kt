package com.app.presentation.component.util

import androidx.compose.runtime.State
import com.app.domain.model.calcul.FormatImpl
import com.app.domain.model.dto.CrewDTO
import com.app.domain.model.location.Coordinate
import com.app.domain.model.state.Activate
import com.app.domain.model.state.ActivateForm
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import java.math.BigDecimal

data class JsonObjImpl(
    val type: String,
    val pedometerCount: Int = 0,
    val activate: StateFlow<Activate>? = null,
    val activateForm: StateFlow<ActivateForm>? = null,
    val runningList: Array<Any>? = null,
    val coordinateList: List<Coordinate> = emptyList(),
    val crewList: State<List<CrewDTO>>? = null
) : JsonObj() {
    override fun build(): JsonObject {

        var data: JsonObject = buildJsonObject { }

        when (type) {
            "cul" -> {
                data = buildJsonObject {
                    put("goal_count", pedometerCount)
                    put("kcal_cul", BigDecimal(pedometerCount * 0.05))
                    put(
                        "km_cul",
                        BigDecimal(FormatImpl("YY:MM:DD:H").calculateDistanceToKm(pedometerCount))
                    )
                }
            }

            "status" -> {
                data = buildJsonObject {
                    put("status_icon", activate!!.value.statusIcon)
                    put("status_title", activate.value.statusName)
                }
            }

            "running" -> {
                data = buildJsonObject {
                    put("running_icon", runningList?.get(0).toString())
                    put("running_title", runningList?.get(1).toString())
                }
            }

            "runningForm" -> {
                data = buildJsonObject {
                    put("running_form_icon", activateForm!!.value.activateFormResId)
                    put("running_form_title", activateForm.value.name)
                }
            }

            "coordinate" -> {
                data = buildJsonObject {
                    put("coords", buildJsonArray {
                        coordinateList.forEach { data ->
                            add(buildJsonObject {
                                put("latitude", data.latitude)
                                put("longitude", data.longitude)
                                put("altitude", data.altitude)
                                put("km", data.km)
                            })
                        }
                    })
                }
            }

            "crewId" -> {
                data = buildJsonObject {
                    put("idx", buildJsonArray {
                        crewList!!.value.forEach { data ->
                            add(buildJsonObject {
                                put("id", data.crewId)
                            })
                        }
                    })
                }
            }
        }

        return data
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as JsonObjImpl

        if (type != other.type) return false
        if (pedometerCount != other.pedometerCount) return false
        if (activate != other.activate) return false
        if (runningList != null) {
            if (other.runningList == null) return false
            if (!runningList.contentEquals(other.runningList)) return false
        } else if (other.runningList != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + pedometerCount
        result = 31 * result + (activate?.hashCode() ?: 0)
        result = 31 * result + (runningList?.contentHashCode() ?: 0)
        return result
    }
}