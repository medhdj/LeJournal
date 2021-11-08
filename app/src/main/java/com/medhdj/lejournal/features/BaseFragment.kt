package com.medhdj.lejournal.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.medhdj.lejournal.R
import com.medhdj.lejournal.databinding.FragmentArticlesFeedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {
    abstract var _binding: ViewDataBinding?
    private val binding get() = _binding!!

}