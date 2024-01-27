package rps

import rps.Players.*

enum class Players {
    Computer,
    Player
}

// unneeded, can use ordinal but this is nice
enum class Weapon(val value: Int) {
    Rock(0),
    Paper(1),
    Scissors(2)
}

enum class Results {
    Champion,
    Death,
    Paradox
}

fun main() {

    val scores = mutableMapOf<Players, Int>(Computer to 0, Player to 0)
    var currentTurn = 0

    while (scores[Player] != 3 && scores[Computer] != 3) {
        when (playRound()) {
            Results.Champion -> {
                println("You killed Computer!")
                scores.replace(Player, scores.getOrDefault(Player, 0) + 1)
            }

            Results.Death -> {
                println("You died.")
                scores.replace(Computer, scores.getOrDefault(Computer, 0) + 1)
            }

            else -> println("You were unable to inflict damage to the enemy Computer.")
        }
        currentTurn++
        scores.entries.forEach { println("${it.key}'s kills: ${it.value}") }
    }

    if (scores[Computer] == 3)
        println("You suffered a horrific defeat at the hands of Computer within $currentTurn turns.")
    else
        println("You have victoriously beheaded Computer within $currentTurn turns.")
}

fun playRound(): Results {
    val computerChoice = getWeaponFromNum(getNumInRangeOfThree())!! // she aint nullable
    var playerChoice: Weapon? = null

    println("Pick your weapon!")
    println("-------------------")
    Weapon.entries.forEach { println("${it.value} - ${it.name}") }

    while (playerChoice == null) {
        val input = getPlayerInput()
        if (input == null || !(0..2).contains(input))
            println("Motherfucker that ain't no weapon")
        else playerChoice = getWeaponFromNum(input)
    }

    println("Computer's weapon of choice: ${computerChoice.name}")
    println("Your weapon of choice: ${playerChoice.name}")

    println("-- FIGHT --")

    return compareWeapons(computerChoice, playerChoice)
}

fun compareWeapons(computerWeapon: Weapon, playerWeapon: Weapon): Results {
    return if (playerWeapon == computerWeapon) Results.Paradox
    else if (
        (computerWeapon == Weapon.Rock && playerWeapon == Weapon.Scissors) ||
        (computerWeapon == Weapon.Scissors && playerWeapon == Weapon.Paper) ||
        (computerWeapon == Weapon.Paper && playerWeapon == Weapon.Rock)
    )
        Results.Death
    else Results.Champion
}

fun getWeaponFromNum(num: Int): Weapon? = Weapon.entries.find { it.value == num }

fun getPlayerInput(): Int? = readln().toIntOrNull()

val getNumInRangeOfThree = { getRandomNumber(3) }

fun getRandomNumber(num: Int): Int {
    return (Math.random() * num).toInt()
}