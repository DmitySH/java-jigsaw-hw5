package bse202.sda.jigsaw.interfaces;

import bse202.sda.jigsaw.utils.IntPoint;

import java.util.List;
import java.util.Optional;

public interface CoordinateTransfer {
    Optional<List<IntPoint>> transferCoordinates();
}
