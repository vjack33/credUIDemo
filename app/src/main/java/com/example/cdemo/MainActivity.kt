package com.example.cdemo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.activity_main.*

import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior

import com.google.android.material.bottomsheet.BottomSheetDialog
import android.animation.Animator

import android.animation.AnimatorListenerAdapter

import android.R.attr.translationY
import android.graphics.Color
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import kotlin.time.minutes


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


        firstSheet = findViewById<View>(R.id.cardView2)
        secondSheet = findViewById<View>(R.id.cardView4)
        thirdSheet = findViewById<View>(R.id.cardView6)
        firstButton = findViewById<View>(R.id.cardView3)
        secondButton = findViewById<View>(R.id.cardView7)
        thirdButton = findViewById<View>(R.id.cardView8)


        firstSheet.setOnClickListener {
            translateDown(secondSheet)
            translateDown(thirdSheet)
            translateDown(secondButton)
            translateDown(thirdButton)
        }

        secondSheet.setOnClickListener {
            translateDown(thirdSheet)
            translateDown(thirdButton)
        }

        firstButton.setOnClickListener {
            translateUp(secondSheet)
            translateUp(secondButton)
        }

        secondButton.setOnClickListener {
            translateUp(thirdSheet)
            translateUp(thirdButton)
        }

        secondSheet.post(Runnable {
            secondSheet.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            translateDown(secondSheet,0)
            translateDown(thirdSheet,0)
            translateDown(secondButton,0)
            translateDown(thirdButton,0)
        })
    }

    override fun onBackPressed() {
        //super.onBackPressed()
     /*   if (thirdSheet.isVisible) {
            translateDown(thirdSheet)
            translateDown(thirdButton)
        } else if (secondSheet.isVisible) {
            translateDown(secondSheet)
            translateDown(secondButton)
        } else {
            super.onBackPressed()
        }*/

        when{
            thirdSheet.isVisible -> {
                translateDown(thirdSheet)
                translateDown(thirdButton)
            }
            secondSheet.isVisible -> {
                translateDown(secondSheet)
                translateDown(secondButton)
            }
            else -> super.onBackPressed()
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

    private fun setupBottomSheetDialogs() {
        val firstBottomSheetDialog = BottomSheetDialog(this)
        val secondBottomSheetDialog = BottomSheetDialog(this)
        val thirdBottomSheetDialog = BottomSheetDialog(this)

        firstBottomSheetDialog.setContentView(R.layout.first_bottom_sheet)
        secondBottomSheetDialog.setContentView(R.layout.second_bottom_sheet)
        thirdBottomSheetDialog.setContentView(R.layout.third_bottom_sheet)

        val copy1 = firstBottomSheetDialog.findViewById<LinearLayout>(R.id.copyLinearLayout)
        val copy2 = secondBottomSheetDialog.findViewById<LinearLayout>(R.id.copyLinearLayout)
        val copy3 = thirdBottomSheetDialog.findViewById<LinearLayout>(R.id.copyLinearLayout)

        firstBottomSheetDialog.show()

        copy1?.setOnClickListener {
            secondBottomSheetDialog.show()
        }

        copy2?.setOnClickListener {
            thirdBottomSheetDialog.show()
        }

        copy3?.setOnClickListener {
            thirdBottomSheetDialog.dismiss()
            secondBottomSheetDialog.dismiss()
        }
    }

    private fun setCardBackgroundColor(view: View, viewColor: String) {
        (view as CardView).setCardBackgroundColor(Color.parseColor(viewColor))
    }

    private fun showBottomSheetDialog(): BottomSheetDialog {
        //How much BottomSheet
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.first_bottom_sheet)
        bottomSheetDialog.behavior.peekHeight = 1000
        val copy = bottomSheetDialog.findViewById<LinearLayout>(R.id.copyLinearLayout)
        val share = bottomSheetDialog.findViewById<LinearLayout>(R.id.shareLinearLayout)
        val upload = bottomSheetDialog.findViewById<LinearLayout>(R.id.uploadLinearLaySout)
        val download = bottomSheetDialog.findViewById<LinearLayout>(R.id.download)
        val delete = bottomSheetDialog.findViewById<LinearLayout>(R.id.delete)
        bottomSheetDialog.show()

        copy?.setOnClickListener {
            showSecondBottomSheetDialog()
        }
        return bottomSheetDialog
    }

    private fun showSecondBottomSheetDialog() {
        //Pick your Repayment BottomSheet
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.second_bottom_sheet)
        val copy = bottomSheetDialog.findViewById<LinearLayout>(R.id.copyLinearLayout)
        val share = bottomSheetDialog.findViewById<LinearLayout>(R.id.shareLinearLayout)
        val upload = bottomSheetDialog.findViewById<LinearLayout>(R.id.uploadLinearLaySout)
        val download = bottomSheetDialog.findViewById<LinearLayout>(R.id.download)
        val delete = bottomSheetDialog.findViewById<LinearLayout>(R.id.delete)
        bottomSheetDialog.show()

        copy?.setOnClickListener {
            showThirdBottomSheetDialog()
        }
    }

    private fun showThirdBottomSheetDialog() {
        //PAdd your Bank Account BottomSheet
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.third_bottom_sheet)
        val copy = bottomSheetDialog.findViewById<LinearLayout>(R.id.copyLinearLayout)
        val share = bottomSheetDialog.findViewById<LinearLayout>(R.id.shareLinearLayout)
        val upload = bottomSheetDialog.findViewById<LinearLayout>(R.id.uploadLinearLaySout)
        val download = bottomSheetDialog.findViewById<LinearLayout>(R.id.download)
        val delete = bottomSheetDialog.findViewById<LinearLayout>(R.id.delete)
        bottomSheetDialog.show()

        share?.setOnClickListener {
            bottomSheetDialog.dismiss()
            bottomSheetDialog.dismiss()
        }
    }
}