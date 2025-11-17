package co.uniandes.grupo11.vinilos.ui.artists

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
import co.uniandes.grupo11.vinilos.ui.adapters.ArtistsAdapter
import co.uniandes.grupo11.vinilos.ui.artists.ArtistDetailFragment
import co.uniandes.grupo11.vinilos.viewmodels.ArtistsViewModel

class ArtistsFragment : Fragment() {
    private lateinit var viewModel: ArtistsViewModel
    private lateinit var artistsRecyclerView: RecyclerView
    private lateinit var artistsAdapter: ArtistsAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyStateText: TextView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_artists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        artistsRecyclerView = view.findViewById(R.id.artists_recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)
        emptyStateText = view.findViewById(R.id.empty_state_text)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)

        artistsAdapter = ArtistsAdapter { artist ->
            val detailFragment = ArtistDetailFragment.newInstance(artist.id)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit()
        }

        artistsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = artistsAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadArtists()
        }

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(ArtistsViewModel::class.java)

        viewModel.artists.observe(viewLifecycleOwner) { artists ->
            artists?.let {
                if (it.isNotEmpty()) {
                    artistsAdapter.updateArtists(it)
                    artistsRecyclerView.visibility = View.VISIBLE
                    emptyStateText.visibility = View.GONE
                } else {
                    artistsRecyclerView.visibility = View.GONE
                    emptyStateText.visibility = View.VISIBLE
                    emptyStateText.text = getString(R.string.no_artists_available)
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
                artistsRecyclerView.visibility = View.GONE
            }
        }
    }
}
