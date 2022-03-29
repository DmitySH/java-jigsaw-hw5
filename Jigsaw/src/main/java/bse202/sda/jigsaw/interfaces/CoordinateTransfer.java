package bse202.sda.jigsaw.interfaces;

import bse202.sda.jigsaw.utils.IntPoint;

import java.util.List;
import java.util.Optional;

/**
 * Allows coordinate transfer between grid.
 */
public interface CoordinateTransfer {
    Optional<List<IntPoint>> transferCoordinates();
}
