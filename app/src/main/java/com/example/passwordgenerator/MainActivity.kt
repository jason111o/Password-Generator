package com.example.passwordgenerator

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {

    // variables representing ascii values for chars
    //val lowerCase = (97..122)
    private val lower = listOf(97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122)
    //val numbers   = (48..57)
    private val number = listOf(48, 49, 50, 51, 52, 53, 54, 55, 56, 57)
    //val upperCase = (65..90)
    private val upper = listOf(65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90)
    //val symbols   = (33..38)
    private val symbol = listOf(33, 36, 38, 46, 63, 64)
    // empty string to store password for returning to TextView
    var passwd: String = ""
    private var passwordLength: Int = 0
    private var element: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val passwordTextView: TextView = findViewById(R.id.textView)
        val strengthTextView: TextView = findViewById(R.id.strengthTextView)
        val numberEditView: EditText = findViewById(R.id.editTextNumber)
        val generateButton: Button = findViewById(R.id.generateButton)
        val lowerCheckBox: CheckBox = findViewById(R.id.lowerCheckBox)
        val numberCheckBox: CheckBox = findViewById(R.id.numbersCheckBox)
        val upperCheckBox: CheckBox = findViewById(R.id.upperCheckBox)
        val symbolCheckBox: CheckBox = findViewById(R.id.symbolCheckBox)
        val ratingsButton: Button = findViewById(R.id.ratingsButton)

        ratingsButton.setOnClickListener {
            passwordTextView.text = ratingsText()
        }

        strengthTextView.visibility = View.INVISIBLE

        generateButton.setOnClickListener {
            if (numberEditView.text.isNullOrBlank()) {
                Toast.makeText(this,"Enter a number",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!lowerCheckBox.isChecked and !numberCheckBox.isChecked and !upperCheckBox.isChecked and !symbolCheckBox.isChecked) {
                Toast.makeText(this,"Must check at least 1 box",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var asciiList = mutableListOf(lower)
            //clean asciilist
            removeElements(asciiList)
            //build ascii list according to boxes selected
            if (lowerCheckBox.isChecked) { asciiList.add(lower) }
            if (numberCheckBox.isChecked) { asciiList.add(number) }
            if (upperCheckBox.isChecked) { asciiList.add(upper) }
            if (symbolCheckBox.isChecked) { asciiList.add(symbol) }

            passwordLength = numberEditView.text.toString().toInt()

            passwd = ""
            element = 0
            for (num in 0 until passwordLength) {
                passwd += asciiList[element][Random.nextInt(0 until asciiList[element].size)].toChar()
                element += 1
                if (element == (asciiList.size)) {
                    element = 0
                }
            }
            passwordTextView.text = randomizeStringChars(passwd)
            strengthTextView.visibility = View.VISIBLE
            scoreGrade(passwordScore(passwd),strengthTextView)
        }
    }

    private fun removeElements(list: MutableList<List<Int>>) {
        for (num in list.indices) {
            list.removeAt(0)
        }
    }
    private fun randomizeStringChars(word: String): String {
        var newString = ""
        val charStore = mutableListOf<String>()
        for (num in word.indices) {
            charStore.add(word[num].toString())
        }
        charStore.shuffle()
        for (num in charStore.indices) {
            newString += charStore[num]
        }
        return newString
    }
    private fun passwordScore(password: String): Int {
        var score = 0
        val bools = mutableMapOf(
                "upper" to false,
                "lower" to false,
                "number" to false,
                "other" to false
        )
        for (char in password) {
            if (char.isLetterOrDigit()) {
                score += 1
            } else {
                score += 1
            }
            when {
                char.isUpperCase() -> bools["upper"] = true
                char.isLowerCase() -> bools["lower"] = true
                char.isDigit() -> bools["number"] = true
                else -> bools["other"] = true
            }
        }
        var boolCount = 0
        for (value in bools.values) {
            if (value)
                boolCount += 1
        }
        if (boolCount == 2)
            score += 3
        else if (boolCount == 3)
            score += 6
        else if (boolCount == 4)
            score += 9
        else
            score += 0

        return score
    }
    private fun scoreGrade(score: Int, textView: TextView) {
        if (score <= 0) {
            textView.text = "Auto Login\nScore: ${score}"
            textView.setTextColor(Color.rgb(255,204,204))
        } else if (score in 1..4) {
            textView.text = "Infant\nScore: ${score}"
            textView.setTextColor(Color.rgb(255,229,204))
        } else if (score in 5..9) {
            textView.text = "Adolescent\nScore: ${score}"
            textView.setTextColor(Color.rgb(229,255,204))
        } else if (score in 10..14) {
            textView.text = "Moderate\nScore: ${score}"
            textView.setTextColor(Color.rgb(204,255,204))
        } else if (score in 15..19) {
            textView.text = "Aggressive\nScore: ${score}"
            textView.setTextColor(Color.rgb(204,255,229))
        } else if (score in 20..29) {
            textView.text = "Whistleblower\nScore: ${score}"
            textView.setTextColor(Color.rgb(204,255,255))
        } else if (score in 30..39) {
            textView.text = "Militant\nScore: ${score}"
            textView.setTextColor(Color.rgb(204,229,255))
        } else if (score in 40..49) {
            textView.text = "Agent\nScore: ${score}"
            textView.setTextColor(Color.rgb(204,204,255))
        } else if (score in 50..59) {
            textView.text = "Krypton Key\nScore: ${score}"
            textView.setTextColor(Color.rgb(229,204,255))
        } else if (score in 60..69){
            textView.text = "Alien Tech\nScore ${score}"
            textView.setTextColor(Color.rgb(255,204,255))
        } else if (score in 70..79) {
            textView.text = "Time Machine\nScore: ${score}"
            textView.setTextColor(Color.rgb(255,204,229))
        } else if (score in 80..89) {
            textView.text = "Immortality Engine\nScore: ${score}"
            textView.setTextColor(Color.rgb(255,255,255))
        } else if (score in 90..99) {
            textView.text = "Secrets To Life\nScore: ${score}"
            textView.setTextColor(Color.rgb(255,255,255))
        } else {
            textView.text = "Secrets of the Almighty Creator\nScore: ${score}"
            textView.setTextColor(Color.rgb(255,255,255))
        }
    }
    private fun ratingsText(): String {
        val text = "SCORE RATING\n" +
                "0       Auto Login\n" +
                "1..4    Infant\n" +
                "5..9    Adolescent\n" +
                "10..14  Moderate\n" +
                "15..19  Aggressive\n" +
                "20..29  Whistleblower\n" +
                "30..39  Militant\n" +
                "40..49  Agent\n" +
                "50..59  Krypton Key\n" +
                "60..69  Alien Tech\n" +
                "70..79  Time Machine\n" +
                "80..89  Immortality Engine\n" +
                "90..99  Secrets To Life\n" +
                "100+    Secrets of the Almighty Creator"
        return text
    }
}