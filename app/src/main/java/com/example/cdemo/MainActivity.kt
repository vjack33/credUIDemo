package com.example.cdemo

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import me.tankery.lib.circularseekbar.CircularSeekBar
import me.tankery.lib.circularseekbar.CircularSeekBar.OnCircularSeekBarChangeListener
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {
    lateinit var firstSheet: View
    lateinit var secondSheet: View
    lateinit var thirdSheet: View
    lateinit var firstButton: View
    lateinit var secondButton: View
    lateinit var thirdButton: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var loanAmount: Int = 0

        window.statusBarColor = Color.parseColor("#12141d")

        firstSheet = findViewById<View>(R.id.hidden1)
        secondSheet = findViewById<View>(R.id.cardView4)
        thirdSheet = findViewById<View>(R.id.cardView6)
        firstButton = findViewById<View>(R.id.cardView3)
        secondButton = findViewById<View>(R.id.cardView7)
        thirdButton = findViewById<View>(R.id.cardView8)

        hidden1.visibility = GONE
        hidden2.visibility = GONE

        setButtonState(firstButton, firstButtonText, false)
        setButtonState(secondButton, secondButtonText, false)
        setButtonState(thirdButton, thirdButtonText, false)

        firstSheet.setOnClickListener {
            Toast.makeText(this, "FirstSheet", Toast.LENGTH_SHORT)
            translateDown(secondSheet)
            translateDown(thirdSheet)
            translateDown(secondButton)
            translateDown(thirdButton)
            hidden1.visibility = GONE
            hidden2.visibility = GONE
        }

        secondSheet.setOnClickListener {
            translateDown(thirdSheet)
            translateDown(thirdButton)
            hidden2.visibility = GONE
        }

        thirdSheet.setOnClickListener {}

        firstButton.setOnClickListener {
            if (loanAmount < 100) {
                Toast.makeText(this, "Please apply for value greater than ₹100", Toast.LENGTH_SHORT)
                    .show()
            } else {
                translateUp(secondSheet)
                translateUp(secondButton)
                hidden1.visibility = VISIBLE
            }
        }

        secondButton.setOnClickListener {
            if (checkbox1.isChecked || checkbox6.isChecked || checkbox12.isChecked) {
                translateUp(thirdSheet)
                translateUp(thirdButton)
                hidden2.visibility = VISIBLE
            } else {
                Toast.makeText(this, "Please select duration for loan", Toast.LENGTH_SHORT).show()
            }
        }

        thirdButton.setOnClickListener {
            Toast.makeText(this, "Nothing ahead, end of Demo", Toast.LENGTH_SHORT).show()
        }

        addAccountButton.setOnClickListener {
            Toast.makeText(this, "Nothing ahead, end of Demo", Toast.LENGTH_SHORT).show()
        }

        secondSheet.post(Runnable {
            secondSheet.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            translateDown(secondSheet, 0)
            translateDown(thirdSheet, 0)
            translateDown(secondButton, 0)
            translateDown(thirdButton, 0)
        })

        checkbox1parent.setOnClickListener {
            checkbox1.isChecked = true
        }
        checkbox6parent.setOnClickListener {
            checkbox6.isChecked = true
        }
        checkbox12parent.setOnClickListener {
            checkbox12.isChecked = true
        }

        checkbox1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkbox12.isChecked = false
                checkbox6.isChecked = false
                emiValue.text = emi1month.text
                durationValue.text = "1 month"
                setButtonState(secondButton, secondButtonText, true)
            }
        }

        checkbox6.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkbox1.isChecked = false
                checkbox12.isChecked = false
                emiValue.text = emi6month.text
                durationValue.text = "6 months"
                setButtonState(secondButton, secondButtonText, true)

            }
        }

        checkbox12.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkbox1.isChecked = false
                checkbox6.isChecked = false
                emiValue.text = emi12month.text
                durationValue.text = "12 months"
                setButtonState(secondButton, secondButtonText, true)
            }
        }

        seek_bar.circleProgressColor = Color.parseColor("#df997f")
        seek_bar.circleColor = Color.parseColor("#fee8e0")
        seek_bar.pointerColor = Color.parseColor("#302A2B")
        seek_bar.setOnSeekBarChangeListener(object : OnCircularSeekBarChangeListener {
            override fun onProgressChanged(circularSeekBar: CircularSeekBar, progress: Float, fromUser: Boolean) {
                Log.d("Main", progress.toString())
                loanAmount = (progress * 100).roundToInt()
                if (loanAmount > 100) {
                    setButtonState(firstButton, firstButtonText,true)
                    loanAmountHidden.text = "₹$loanAmount"
                    amountText.text = "₹$loanAmount"
                    emi1month.text = "₹$loanAmount / mo"
                    emi6month.text = "₹${(loanAmount / 6)} /mo"
                    emi12month.text = "₹${loanAmount / 12} /mo"
                } else
                    setButtonState(firstButton, firstButtonText, false)
            }

            override fun onStopTrackingTouch(seekBar: CircularSeekBar) {
                Log.d("Main", "onStopTrackingTouch")
            }

            override fun onStartTrackingTouch(seekBar: CircularSeekBar) {
                Log.d("Main", "onStartTrackingTouch")
            }
        })

    }

    override fun onBackPressed() {
        when {
            thirdSheet.isVisible -> {
                translateDown(thirdSheet)
                translateDown(thirdButton)
                hidden2.visibility = GONE
            }
            secondSheet.isVisible -> {
                translateDown(secondSheet)
                translateDown(secondButton)
                hidden1.visibility = GONE
            }
            else -> super.onBackPressed()
        }
    }

    private fun setButtonState(buttonCardView: View, buttonTextView: View, isEnabled: Boolean) {
        if (isEnabled) {
            (buttonCardView as CardView).setCardBackgroundColor(Color.parseColor("#3a45a1"))
            (buttonTextView as TextView).setTextColor(Color.parseColor("#fee8e0"))
        } else {
            (buttonCardView as CardView).setCardBackgroundColor(Color.parseColor("#282f5d"))
            (buttonTextView as TextView).setTextColor(Color.parseColor("#6B5B62"))
        }
    }

    private fun translateDown(view: View, time: Int = 300) {
        view.animate().translationY(view.height.toFloat())
            .alpha(0.0f)
            .setDuration(time.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    view.visibility = GONE
                }
            })
    }

    private fun translateUp(view: View, time: Int = 300) {
        view.visibility = VISIBLE
        view.animate().translationY(0F)
            .alpha(1.0f)
            .setDuration(time.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    view.visibility = VISIBLE
                }
            })
    }
}