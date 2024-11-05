package com.freepath.freepath.data.plan


import com.freepath.freepath.presentation.model.Recommend
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class RecommendRequest(
    @SerialName("age")
    val ages: List<Int>,
    @SerialName("destination")
    val destination: String,
    @SerialName("disabilities")
    val disabilities: List<String>,
    @SerialName("endAt")
    val endAt: String,
    @SerialName("environment")
    val environment: Int,
    @SerialName("people")
    val people: Int,
    @SerialName("purpose")
    val purpose: List<Int>,
    @SerialName("startAt")
    val startAt: String,
    @SerialName("theme")
    val theme: List<Int>,
    @SerialName("visit")
    val visit: List<Int>
)

fun Recommend.toRecommendRequest() = RecommendRequest(
    ages = ages,
    destination = destination,
    disabilities = disabilities.map { it.name },
    endAt = lastDay.toString(),
    startAt = firstDay.toString(),
    environment = environments,
    people = peopleCount,
    purpose = themes,
    theme = targets,
    visit = visits
)

fun main() {
    println(LocalDate.now().toString())
}