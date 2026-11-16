package com.example.tp_11___composant

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var circularScoreView: CircularScoreView
    private lateinit var btnIncrementScore: Button
    private lateinit var ratingBarView: RatingBarView
    private lateinit var tvRatingResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        circularScoreView = findViewById(R.id.circularScoreView)
        btnIncrementScore = findViewById(R.id.btnIncrementScore)
        ratingBarView = findViewById(R.id.ratingBarView)
        tvRatingResult = findViewById(R.id.tvRatingResult)

        btnIncrementScore.setOnClickListener {
            var newScore = circularScoreView.score + 10
            if (newScore > circularScoreView.maxScore) {
                newScore = 0
            }
            circularScoreView.score = newScore
        }

        ratingBarView.onRatingChangedListener = { rating ->
            tvRatingResult.text = getString(R.string.rating_format, rating, ratingBarView.maxRating)
        }
    }
}
