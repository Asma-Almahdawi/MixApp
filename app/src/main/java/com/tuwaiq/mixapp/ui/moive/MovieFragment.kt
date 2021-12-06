package com.tuwaiq.mixapp.ui.moive

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tuwaiq.mixapp.R
import com.tuwaiq.mixapp.databinding.MovieFragmentBinding
import com.tuwaiq.mixapp.databinding.MovieListItemBinding
import com.tuwaiq.mixapp.imdb.models.Movie
import kotlinx.coroutines.flow.collect

class MovieFragment : Fragment() {

    companion object {
        fun newInstance() = MovieFragment()
    }

    private val  movieViewModel: MovieViewModel by lazy { ViewModelProvider(this)[MovieViewModel::class.java] }

    private lateinit var binding:MovieFragmentBinding

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu,menu)

        val searchView = menu.findItem(R.id.search_action).actionView as SearchView

        searchView.apply {

            setOnCloseListener {
                movieViewModel.sendQuery("")
                false }

            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null){
                        movieViewModel.sendQuery(query)
                    }

                   return true
                }

                override fun onQueryTextChange(newText: String?): Boolean = false

            })
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

       binding = MovieFragmentBinding.inflate(layoutInflater)
       binding.movieRv.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieViewModel.dataLiveData.observe(
            viewLifecycleOwner,
            Observer {
                binding.movieRv.adapter = MoviesAdapter(it)
            }
        )
    }

    private inner class MoviesHolder(val binding: MovieListItemBinding)
        :RecyclerView.ViewHolder(binding.root){

            fun bind(movie: Movie){
                if (movie.imDbRating.isEmpty()){
                    binding.ratingBar.visibility = View.GONE
                }else{
                    binding.ratingBar.rating = movie.imDbRating.toFloat()

                }
                binding.titleTv.text = movie.title
                binding.yearTv.text = movie.year
                binding.posterIv.load(movie.img_url)
            }
        }


    private inner class MoviesAdapter(val movies:List<Movie>):
            RecyclerView.Adapter<MoviesHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
            val binding = MovieListItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return MoviesHolder(binding)

        }

        override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
           val movie = movies[position]
            holder.bind(movie)
        }

        override fun getItemCount(): Int  = movies.size
    }


}