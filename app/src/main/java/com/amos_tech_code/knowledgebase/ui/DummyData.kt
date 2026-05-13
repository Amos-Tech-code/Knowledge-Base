package com.amos_tech_code.knowledgebase.ui

// Data classes
data class KnowledgeItem(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val emoji: String
)

data class Quiz(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctAnswer: Int,
    val points: Int
)

// Dummy data providers
object DummyData {
    val knowledgeItems = listOf(
        KnowledgeItem(
            1, "The Internet's Weight",
            "The entire internet weighs about the same as a strawberry! (50 grams) All the electrons that make up internet data have a tiny amount of mass.",
            "Technology", "🌐"
        ),
        KnowledgeItem(
            2, "Quantum Entanglement",
            "When two particles become entangled, they can influence each other instantly across any distance - even from one end of the universe to the other!",
            "Science", "⚛️"
        ),
        KnowledgeItem(
            3, "The Great Pyramid",
            "The Great Pyramid of Giza was the tallest man-made structure for over 3,800 years. Its construction used enough stone to build a wall around France!",
            "History", "🔺"
        ),
        KnowledgeItem(
            4, "Mona Lisa's Secret",
            "The Mona Lisa has a hidden portrait underneath the visible painting. Scientists discovered it using special reflective light techniques.",
            "Arts", "🎨"
        ),
        KnowledgeItem(
            5, "Black Hole Sounds",
            "Black holes emit the deepest note in the universe - a B-flat, 57 octaves below middle C. Humans can't hear it, but it's real!",
            "Space", "🕳️"
        ),
        KnowledgeItem(
            6, "Immortal Jellyfish",
            "There's a jellyfish (Turritopsis dohrnii) that can theoretically live forever by reverting to its juvenile form after reaching adulthood.",
            "Nature", "🎐"
        ),
        KnowledgeItem(
            7, "Cloud Computing",
            "The cloud actually uses more electricity than the entire country of Germany. Data centers worldwide consume about 1% of global electricity!",
            "Technology", "☁️"
        ),
        KnowledgeItem(
            8, "Your Brain's Power",
            "Your brain generates about 12-25 watts of electricity - enough to power a low-wattage LED light bulb!",
            "Science", "🧠"
        ),
        KnowledgeItem(
            9, "Viking Sunstones",
            "Vikings used special crystals called 'sunstones' to navigate on cloudy days by detecting the polarization of sunlight.",
            "History", "⚓"
        ),
        KnowledgeItem(
            10, "Van Gogh's Ear",
            "Van Gogh only sold one painting during his lifetime. 'The Red Vineyard' sold for 400 francs in Belgium.",
            "Arts", "👂"
        ),
        KnowledgeItem(
            11, "Space Smells",
            "Astronauts describe space as smelling like seared steak, hot metal, and welding fumes due to dying stars!",
            "Space", "👃"
        ),
        KnowledgeItem(
            12, "Octopus Hearts",
            "Octopuses have three hearts - two pump blood to the gills, while one pumps it to the rest of the body.",
            "Nature", "🐙"
        )
    )

    val quizzes = listOf(
        Quiz(
            1,
            "What did astronauts say space smells like?",
            listOf("Roses", "Seared steak", "Chocolate", "Fresh laundry"),
            1,
            20
        ),
        Quiz(
            2,
            "How many hearts does an octopus have?",
            listOf("One", "Two", "Three", "Four"),
            2,
            20
        ),
        Quiz(
            3,
            "What note do black holes emit?",
            listOf("C", "A-sharp", "B-flat", "G"),
            2,
            25
        ),
        Quiz(
            4,
            "How much of global electricity do data centers use?",
            listOf("About 0.1%", "About 1%", "About 5%", "About 10%"),
            1,
            25
        ),
        Quiz(
            5,
            "Which jellyfish can live forever?",
            listOf("Moon jellyfish", "Box jellyfish", "Turritopsis dohrnii", "Lion's mane jellyfish"),
            2,
            30
        )
    )

    val funFacts = listOf(
        "A day on Venus is longer than a year on Venus!",
        "Honey never spoils. Archaeologists found 3000-year-old honey in Egyptian tombs that's still edible!",
        "The world's oldest piece of chewing gum is 9,000 years old!",
        "Octopuses have three hearts and blue blood!",
        "Bananas are berries, but strawberries aren't!",
        "A cloud weighs around a million tons!",
        "There's a species of jellyfish that is biologically immortal!",
        "The human nose can remember 50,000 different scents!",
        "Your brain is more active when you sleep than when watching TV!",
        "The Eiffel Tower can be 15 cm taller during summer due to thermal expansion!"
    )
}