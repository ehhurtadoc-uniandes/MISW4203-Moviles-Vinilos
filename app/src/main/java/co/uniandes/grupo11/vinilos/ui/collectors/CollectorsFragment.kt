package co.uniandes.grupo11.vinilos.ui.collectors

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
import co.uniandes.grupo11.vinilos.ui.adapters.CollectorsAdapter
import co.uniandes.grupo11.vinilos.viewmodels.CollectorsViewModel

class CollectorsFragment : Fragment() {
    private lateinit var viewModel: CollectorsViewModel
    private lateinit var collectorsRecyclerView: RecyclerView
    private lateinit var collectorsAdapter: CollectorsAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyStateText: TextView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_collectors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectorsRecyclerView = view.findViewById(R.id.collectors_recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)
        emptyStateText = view.findViewById(R.id.empty_state_text)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)

        collectorsAdapter = CollectorsAdapter()

        collectorsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = collectorsAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadCollectors()
        }

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(CollectorsViewModel::class.java)

        viewModel.collectors.observe(viewLifecycleOwner) { collectors ->
            collectors?.let {
                if (it.isNotEmpty()) {
                    collectorsAdapter.updateCollectors(it)
                    collectorsRecyclerView.visibility = View.VISIBLE
                    emptyStateText.visibility = View.GONE
                } else {
                    collectorsRecyclerView.visibility = View.GONE
                    emptyStateText.visibility = View.VISIBLE
                    emptyStateText.text = "No hay coleccionistas disponibles"
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
                collectorsRecyclerView.visibility = View.GONE
            }
        }
    }
}
