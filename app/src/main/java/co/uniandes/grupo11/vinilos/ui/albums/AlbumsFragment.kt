package co.uniandes.grupo11.vinilos.ui.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import co.uniandes.grupo11.vinilos.R
import co.uniandes.grupo11.vinilos.adapters.AlbumsAdapter
import co.uniandes.grupo11.vinilos.viewmodels.AlbumsViewModel

class AlbumsFragment : Fragment() {
    private lateinit var viewModel: AlbumsViewModel
    private lateinit var albumsRecyclerView: RecyclerView
    private lateinit var albumsAdapter: AlbumsAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyStateText: TextView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_albums, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        albumsRecyclerView = view.findViewById(R.id.albums_recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)
        emptyStateText = view.findViewById(R.id.empty_state_text)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)

        albumsAdapter = AlbumsAdapter { album ->
            val detailFragment = AlbumDetailFragment.newInstance(album.id)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit()
        }

        albumsRecyclerView.apply {
            layoutManager = androidx.recyclerview.widget.GridLayoutManager(requireContext(), 2)
            adapter = albumsAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadAlbums()
        }

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(AlbumsViewModel::class.java)

        viewModel.albums.observe(viewLifecycleOwner) { albums ->
            albums?.let {
                if (it.isNotEmpty()) {
                    albumsAdapter.updateAlbums(it)
                    albumsRecyclerView.visibility = View.VISIBLE
                    emptyStateText.visibility = View.GONE
                } else {
                    albumsRecyclerView.visibility = View.GONE
                    emptyStateText.visibility = View.VISIBLE
                    emptyStateText.text = "No hay álbumes disponibles"
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                if (!swipeRefreshLayout.isRefreshing) {
                    progressBar.visibility = View.VISIBLE
                }
                emptyStateText.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                swipeRefreshLayout.isRefreshing = false
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            errorMsg?.let { msg ->
                Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
                emptyStateText.visibility = View.VISIBLE
                emptyStateText.text = msg
                albumsRecyclerView.visibility = View.GONE
            }
        }
    }
}

