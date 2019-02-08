package test.com.simpleappjetpack.ui.main
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.main_fragment.*
import test.com.simpleappjetpack.R
import test.com.simpleappjetpack.utils.Engine

class MainFragment : Fragment() , View.OnClickListener, View.OnTouchListener{
    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.message -> sendWeatherRequest()
            }
        }
    }

    private fun sendWeatherRequest() {
        searchCity.setText(message.text)
        searchCity.setSelection(message.text.lastIndex+1)
        cityId = viewModel.getCityId(activity!!.applicationContext, message.text.toString())
    }

    private lateinit var viewModel: MainViewModel
    private var cityId : Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        SetTextWatcherForEditView(searchCity)
        message.setOnClickListener(this)
        searchCity.setOnTouchListener(this)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSearch.setOnClickListener {
            val cityToSend = Bundle()
            cityToSend.putInt("cityId", cityId)
            if(cityId == 0)
                Engine.newInstance().showMsgDialog("", "Try again!", activity!!)
            else
                it.findNavController().navigate(R.id.toDetailWeatherFragment, cityToSend)
        }
    }

    private fun SetTextWatcherForEditView(amountEditText: EditText) {
        val fieldValidatorTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (filterLongEnough(amountEditText)) {
                    val city =  viewModel.getCity(activity!!.applicationContext, searchCity.text.toString())
                    if(!city!!.isEmpty()!!){
                        message.text= city
                    }
                }
            }
        }
        amountEditText.addTextChangedListener(fieldValidatorTextWatcher)
    }

    private fun filterLongEnough(amountEditText: TextView): Boolean {
        return amountEditText.text.toString().isNotEmpty()
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val DRAWABLE_RIGHT = 2
        if (event.action == MotionEvent.ACTION_UP) {
            if (v === searchCity) {
                try {
                    if (event.rawX >= searchCity.right - searchCity.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                        searchCity.setText("")
                        return true
                    }
                } catch (e: Exception) {
                    e.printStackTrace()

                }
            }
        }
        return false
    }

}
