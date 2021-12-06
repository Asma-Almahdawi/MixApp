package com.tuwaiq.mixapp.ui.room

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuwaiq.mixapp.R
import com.tuwaiq.mixapp.database.Name
import com.tuwaiq.mixapp.databinding.NameListItemBinding
import com.tuwaiq.mixapp.databinding.RoomFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RoomFragment : Fragment() {

    companion object {
        fun newInstance() = RoomFragment()
    }

    private lateinit var navController:NavController

    private val viewModel: RoomViewModel by lazy { ViewModelProvider(this)[RoomViewModel::class.java] }

    private lateinit var binding: RoomFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        navController = findNavController()

        binding = RoomFragmentBinding.inflate(layoutInflater)

        binding.roomRv.layoutManager = LinearLayoutManager(context)

        binding.addName.setOnClickListener {
            val action = RoomFragmentDirections.actionNavigationRoomToRoomDetailsFragment()
            navController.navigate(action)
        }

        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO) {
            while (true){
                val name = Name()
                name.name = "ahmed"
                viewModel.addName(name)
                delay(5000)
            }
        }


        viewModel.namesLiveData.observe(viewLifecycleOwner){names ->
            binding.roomRv.adapter = NamesAdapter(names)
        }
    }


    private inner class NameHolder(val binding:NameListItemBinding)
        :RecyclerView.ViewHolder(binding.root),View.OnClickListener{


            private lateinit var name: Name

            init {
                itemView.setOnClickListener(this)
            }

            fun bind(name:Name){
                this.name = name

                binding.nameTv.text = name.name
            }

        override fun onClick(v: View?) {
            val action = RoomFragmentDirections.actionNavigationRoomToRoomDetailsFragment(name.id)
            navController.navigate(action)
        }

    }

    private inner class NamesAdapter(val names:List<Name>)
        :RecyclerView.Adapter<NameHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameHolder {
            val binding  = NameListItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return NameHolder(binding)
        }

        override fun onBindViewHolder(holder: NameHolder, position: Int) {
            val name = names[position]
            holder.bind(name)
        }

        override fun getItemCount(): Int = names.size

    }




}