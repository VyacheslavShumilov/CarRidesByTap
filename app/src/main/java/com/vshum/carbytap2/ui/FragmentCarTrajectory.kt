package com.vshum.carbytap2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.vshum.carbytap2.databinding.FragmentCarPathBinding
import com.vshum.carbytap2.di.Scopes
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent

class FragmentCarTrajectory: Fragment(), PathView {

    private val scope = KoinJavaComponent.getKoin().createScope<FragmentCarTrajectory>()
    private val presenter: PathPresenter = scope.get(qualifier = named(Scopes.PATH_PRESENTER))

    private var _binding: FragmentCarPathBinding? = null
    private val binding
        get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCarPathBinding.inflate(inflater, container, false)
        setClickListener()
        presenter.pathView = this

        //после создания всех view передаем размеры в прзентер
        binding?.root?.post {
            binding?.let {
                //заполняем размер поля и размер машинки
                presenter.setRegionSize(it.region.measuredWidth, it.region.measuredHeight)
                presenter.setCarSize(it.car.measuredWidth, it.car.measuredHeight)
            }
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Поставить машинку на конретные координаты.
     * @param left отступ машинки слева.
     * @param top отступ машинки сверху.
     */
    override fun setCarPosition(left: Int, top: Int) {
        binding?.let {
            val layoutParams: LinearLayout.LayoutParams = it.car.layoutParams as LinearLayout.LayoutParams
            layoutParams.leftMargin = left
            layoutParams.topMargin = top
            it.car.layoutParams = layoutParams
            it.car.isVisible = true
            it.region.points = null
            it.region.invalidate()
        }
    }

    /**
     * Нарисовать маршрут машинки.
     * @param points список пар отсупов слева и сверху.
     * @param carWidth ширина машинки.
     * @param carHeight высота машинки.
     */
    override fun printRoute(points: List<Pair<Int, Int>>, carWidth: Int, carHeight: Int) {
        binding?.let {
            it.region.points = points
            it.region.carWidth = carWidth
            it.region.carHeight = carHeight
            it.region.invalidate()
        }
    }


    private fun setClickListener() {
        binding?.let {
            //установить машину в новое место
            it.start.setOnClickListener { v ->
                presenter.resetCarPosition(it.car)
                it.car.isEnabled = true
            }

            //клик по машине
            it.car.setOnClickListener { v ->
                v.isEnabled = false //не разрешаем кликать по машинке пока она едет
                presenter.startMovement(v)
            }
        }
    }
}