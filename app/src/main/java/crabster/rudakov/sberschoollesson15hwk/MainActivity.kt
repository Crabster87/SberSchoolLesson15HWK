package crabster.rudakov.sberschoollesson15hwk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import crabster.rudakov.sberschoollesson15hwk.Constants.COUNT_OF_FRAGMENTS_IN_BACKSTACK
import crabster.rudakov.sberschoollesson15hwk.fragments.FirstFragment
import crabster.rudakov.sberschoollesson15hwk.fragments.SecondFragment

class MainActivity : AppCompatActivity() {

    private lateinit var firstFragment: FirstFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /** Создаётся 2 фрагмента, второй - с количеством '0' в 'TextView' **/
        if (savedInstanceState == null) {
            val bundle: Bundle = bundleOf(COUNT_OF_FRAGMENTS_IN_BACKSTACK to 0)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<FirstFragment>(R.id.first_fragment_container)
                add<SecondFragment>(R.id.second_fragment_container, args = bundle)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        firstFragment =
            supportFragmentManager.findFragmentById(R.id.first_fragment_container) as FirstFragment

        firstFragment.requireView().findViewById<Button>(R.id.add_button).apply {
            /** По нажатию кнопки 'ADD' собирается транзакция с требуемыми параметрами **/
            setOnClickListener {
                val isCheckedRadioButton = verifyRadioButtons()
                /** В случае наличия галочки у чек-бокса счётчик фрагментов увеличивается **/
                var countFragmentsBackstack = supportFragmentManager.backStackEntryCount
                if (verifyCheckBox()) countFragmentsBackstack++
                /** В 'Bundle' по ключу обновляется счётчик фрагментов **/
                val recountedBundle: Bundle =
                    bundleOf(COUNT_OF_FRAGMENTS_IN_BACKSTACK to countFragmentsBackstack)
                /** В зависимости от нажатой кнопки из 'RadioButton' формируется транзакция **/
                supportFragmentManager.commit {
                    when (isCheckedRadioButton) {
                        R.id.radio_button_add -> add<SecondFragment>(
                            R.id.second_fragment_container,
                            args = recountedBundle
                        )
                        R.id.radio_button_replace -> replace<SecondFragment>(
                            R.id.second_fragment_container,
                            args = recountedBundle
                        )
                    }
                    /** Упорядочивается смена состояний фрагментов **/
                    setReorderingAllowed(true)
                    /** В случае наличия галочки у чек-бокса 'Add fragment to backstack' **/
                    if (verifyCheckBox()) addToBackStack(null)
                }
            }
        }
        /** Задаётся функционал кнопки 'REMOVE' **/
        firstFragment.requireView().findViewById<Button>(R.id.remove_button).apply {
            setOnClickListener {
                removeSecondFragment()
            }
        }
    }

    /**
     * Метод возвращает состояние ID нажатой кнопки из 'RadioButton'
     **/
    private fun verifyRadioButtons(): Int {
        val radioGroup: RadioGroup = firstFragment.requireView().findViewById(R.id.radio_group)
        return radioGroup.checkedRadioButtonId
    }

    /**
     * Метод возвращает состояние чек-бокса 'Add fragment to backstack'
     **/
    private fun verifyCheckBox(): Boolean {
        val checkBox: CheckBox = firstFragment.requireView().findViewById(R.id.check_box_backstack)
        return checkBox.isChecked
    }

    /**
     * Метод извлекает фрагмент из 'BackStack'
     **/
    private fun removeSecondFragment() {
        supportFragmentManager.popBackStack()
    }

}