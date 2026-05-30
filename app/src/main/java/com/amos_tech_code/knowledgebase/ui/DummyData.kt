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
            1, "M-Pesa: The Global Icon",
            "Launched in Kenya in 2007, M-Pesa was the world's first successful mobile money system. It started as a way to repay microloans and turned Kenya into a global leader in digital finance!",
            "Technology", "📲"
        ),
        KnowledgeItem(
            2, "Kipchoge's 1:59 Challenge",
            "In 2019, Eliud Kipchoge became the first human to run a marathon in under 2 hours. He ran at a pace of 2:50 per kilometer—a speed most people can't even maintain on a bike for that long!",
            "Sports", "🏃‍♂️"
        ),
        KnowledgeItem(
            3, "The Silicon Savannah",
            "Nairobi is known as the 'Silicon Savannah' because it's one of the top tech hubs in Africa. Major companies like Google, Microsoft, and Amazon have set up big offices right here in the 254!",
            "Technology", "🇰🇪"
        ),
        KnowledgeItem(
            4, "Matatu Art Galleries",
            "Nairobi's Matatu culture is unique in the world. These 'Manyangas' aren't just buses; they are moving art galleries with high-tech sound systems, Wi-Fi, and custom graffiti.",
            "Culture", "🚌"
        ),
        KnowledgeItem(
            5, "TikTok's Secret Sauce",
            "The TikTok algorithm doesn't care who you follow. It tracks exactly how many seconds you watch a video. If you pause for even a split second, it learns your hidden interests!",
            "Digital", "🎥"
        ),
        KnowledgeItem(
            6, "GTA V vs. Hollywood",
            "Grand Theft Auto V earned $1 billion in just 3 days. That's faster and more money than almost any Hollywood blockbuster movie in history!",
            "Gaming", "🎮"
        ),
        KnowledgeItem(
            7, "The Great Migration",
            "The Masai Mara hosts the '8th Wonder of the World'. Over 1.5 million wildebeest migrate every year, a spectacle so big it can be seen from space!",
            "Nature", "🦓"
        ),
        KnowledgeItem(
            8, "Diamond Rain",
            "On Jupiter and Saturn, it actually rains diamonds! Intense pressure in their atmospheres turns carbon into crystals that fall toward the core.",
            "Space", "💎"
        ),
        KnowledgeItem(
            9, "AI Can 'Dream'",
            "Modern AI like Midjourney or ChatGPT uses 'Neural Networks' inspired by the human brain. When they generate art, they are basically 'dreaming' up new patterns from billions of images.",
            "Technology", "🤖"
        ),
        KnowledgeItem(
            10, "Taifa-1: Kenya's Eye in Space",
            "In 2023, Kenya launched Taifa-1, its first operational satellite. It orbits the Earth monitoring agriculture and environmental changes in the 254 from space!",
            "Space", "🛰️"
        ),
        KnowledgeItem(
            11, "Space Suit Cost",
            "A single NASA space suit costs $12 million! 70% of that is for the backpack and control module. It's basically a one-person spacecraft.",
            "Space", "👩‍🚀"
        ),
        KnowledgeItem(
            12, "Gaming & Surgery",
            "Surgeons who grew up playing video games for more than 3 hours a week make 37% fewer mistakes and perform 27% faster than those who don't play!",
            "Gaming", "👨‍⚕️"
        ),
        KnowledgeItem(
            13, "Your Unlimited Brain",
            "Your brain's storage capacity is virtually unlimited. It can hold about 2.5 petabytes of data—that's enough to store 3 million hours of TV shows!",
            "Science", "🧠"
        ),
        KnowledgeItem(
            14, "The Emu War",
            "In 1932, the Australian military lost a 'war' against 20,000 Emus. The birds were too fast and smart, and the military eventually gave up!",
            "History", "🐦"
        ),
        KnowledgeItem(
            15, "Laser From Space",
            "NASA recently sent a high-definition cat video from 19 million miles away in deep space using a laser. It's 100x faster than traditional radio waves!",
            "Space", "🐱"
        ),
        KnowledgeItem(
            16, "Victor Wanyama: The Lion",
            "Victor Wanyama was the first Kenyan to play in the English Premier League. He made history when he joined Southampton and later became a star at Tottenham Hotspur!",
            "Sports", "⚽"
        ),
        KnowledgeItem(
            17, "BRCK: Made in Kenya",
            "BRCK is a rugged, water-resistant router designed and built in Nairobi. It was made specifically to provide reliable internet in areas where electricity is unstable.",
            "Technology", "🧱"
        ),
        KnowledgeItem(
            18, "The Lunatic Line",
            "The Kenya-Uganda railway was nicknamed the 'Lunatic Line' because it was incredibly expensive and dangerous to build, famously involving man-eating lions!",
            "History", "🚂"
        ),
        KnowledgeItem(
            19, "Elephant Rumbles",
            "Elephants communicate using deep 'rumbles' at frequencies so low that humans can't hear them. These vibrations can travel miles through the ground!",
            "Nature", "🐘"
        ),
        KnowledgeItem(
            20, "Bitcoin in the 254",
            "Kenya consistently has one of the highest peer-to-peer Bitcoin trading volumes in the world. Young Kenyans use it for everything from savings to global freelance payments!",
            "Digital", "₿"
        ),
        KnowledgeItem(
            21, "Fastest African Fiber",
            "Kenya has some of the fastest internet speeds in Africa, often beating several European countries, thanks to multiple deep-sea fiber cables landing at Mombasa.",
            "Technology", "🔌"
        ),
        KnowledgeItem(
            22, "Shujaa: Singapore Kings",
            "In 2016, the Kenya Sevens (Shujaa) made history by winning their first-ever World Rugby Sevens Series title in Singapore, beating the world champions Fiji in the final!",
            "Sports", "🏉"
        ),
        KnowledgeItem(
            23, "Man-Eaters of Tsavo",
            "Two lions in Tsavo once stopped the construction of the Kenya-Uganda railway for months. They were so cunning that workers believed they were spirits in lion form!",
            "History", "🦁"
        ),
        KnowledgeItem(
            24, "Crypto-Art (NFTs)",
            "Kenyan artists are now selling digital art globally as NFTs. One Kenyan photographer sold a collection for millions of shillings using blockchain technology!",
            "Digital", "🖼️"
        ),
        KnowledgeItem(
            25, "Gengetone Magic",
            "Gengetone is a unique Kenyan music subgenre that started in DIY home studios. It proved that young Kenyans don't need big budgets to create a national culture shift!",
            "Culture", "🎶"
        )
    )

    val quizzes = listOf(
        Quiz(
            1,
            "In what year was M-Pesa launched in Kenya?",
            listOf("2005", "2007", "2010", "2013"),
            1,
            30
        ),
        Quiz(
            2,
            "What was Eliud Kipchoge's historic marathon time in Vienna?",
            listOf("2:01:39", "1:59:40", "2:05:00", "1:58:22"),
            1,
            30
        ),
        Quiz(
            3,
            "Which city is known as the 'Silicon Savannah'?",
            listOf("Lagos", "Cape Town", "Nairobi", "Accra"),
            2,
            20
        ),
        Quiz(
            4,
            "What was Kenya's first operational satellite called?",
            listOf("KenyaSat-1", "Taifa-1", "Nairobi-Eye", "Safari-Sat"),
            1,
            25
        ),
        Quiz(
            5,
            "Which Kenyan was the first to play in the English Premier League?",
            listOf("McDonald Mariga", "Victor Wanyama", "Michael Olunga", "Dennis Oliech"),
            1,
            25
        ),
        Quiz(
            6,
            "Where did the Kenya 7s (Shujaa) win their first World Series title?",
            listOf("Dubai", "London", "Singapore", "Hong Kong"),
            2,
            30
        ),
        Quiz(
            7,
            "What was the Kenya-Uganda railway nicknamed?",
            listOf("The Iron Snake", "The Lunatic Line", "The Safari Express", "The Mombasa Way"),
            1,
            20
        ),
        Quiz(
            8,
            "How many hearts does an octopus have?",
            listOf("One", "Two", "Three", "Four"),
            2,
            20
        ),
        Quiz(
            9,
            "Which game earned $1 billion in just 3 days?",
            listOf("PUBG", "Free Fire", "GTA V", "FIFA 24"),
            2,
            20
        )
    )

    val funFacts = listOf(
        "Kenya is the only country in the world with a National Park inside its capital city! 🦁",
        "Matatus in Nairobi often have names like 'The Matrix' or 'Vybz Kartel'. 🚌",
        "You can't hum while holding your nose. (Go on, try it!) 👃",
        "The word 'Safari' is Swahili for 'Journey'. 🦁",
        "Mount Kenya is the only place on the equator where you can find permanent glaciers! ❄️",
        "Nairobi means 'Place of Cool Waters' in Maasai. 💧",
        "A lion's roar can be heard from 8 kilometers away! 🦁",
        "Kenya is the world's leading exporter of black tea. ☕",
        "The world's largest concentration of flamingos is at Lake Nakuru! 🦩",
        "Honeybees can recognize human faces! 🐝",
        "There is a planet made almost entirely of diamonds! 🪐",
        "You share 50% of your DNA with a banana! 🍌",
        "The first text message ever sent said 'Merry Christmas'. ✉️",
        "Pringles are legally 'potato crisps', not potato chips. 🍟",
        "A bolt of lightning is 5x hotter than the surface of the sun! ⚡",
        "Otters hold hands when they sleep so they don't drift apart. 🦦",
        "Kangaroos can't walk backwards. 🦘",
        "Kenya's Lake Turkana is the world's largest permanent desert lake! 🌊",
        "Your phone has more computing power than the computers used for the Moon landing! 📱",
        "A single strand of spaghetti is called a 'spaghetto'. 🍝"
    )
}
