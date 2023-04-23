package com.utsman.osmandcompose

import androidx.compose.runtime.AbstractApplier

internal class MapApplier(
    val mapView: OsmMapView
) : AbstractApplier<OsmAndNode>(OsmNodeRoot) {

    private val decorations = mutableListOf<OsmAndNode>()

    override fun insertBottomUp(index: Int, instance: OsmAndNode) {
        decorations.add(index, instance)
        instance.onAttached()
    }

    override fun insertTopDown(index: Int, instance: OsmAndNode) {
    }

    override fun move(from: Int, to: Int, count: Int) {
        decorations.move(from, to, count)
    }

    override fun onClear() {
        mapView.overlayManager.clear()
        decorations.forEach { it.onCleared() }
        decorations.clear()
    }

    override fun remove(index: Int, count: Int) {
        repeat(count) {
            decorations[index + it].onRemoved()
        }
        decorations.remove(index, count)
    }

    internal fun invalidate() = mapView.postInvalidate()

}