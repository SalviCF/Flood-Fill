def floodFill(src: String, dest: String,
              startX: Int, startY: Int,
              compareColors: (java.awt.Color, java.awt.Color) => Boolean,
              fillColor: java.awt.Color) = {

  // Preprocessing
  val raw = javax.imageio.ImageIO.read(new java.io.File(src))
  val (width, height) = (raw.getWidth, raw.getHeight)
  val toVisitPixels = collection.mutable.ArrayDeque((startX, startY))
  val seenPixels = collection.mutable.Set.empty[(Int, Int)]

  // Processing
  while (toVisitPixels.nonEmpty) {
    val (currPixelX, currPixelY) = toVisitPixels.removeHead()

    // Save and set current pixel color
    val currPixelColor = new java.awt.Color(raw.getRGB(currPixelX, currPixelY))
    raw.setRGB(currPixelX, currPixelY, fillColor.getRGB)

    // Breadth-first traverse: searching sufficiently similar adjacent pixels
    def propagate() = {
      val relativePositions = List((-1,0), (1,0), (0,-1), (0,1))
      val adjacentPixels =  for ((dx, dy) <- relativePositions)
                            yield (currPixelX + dx, currPixelY + dy)
      val insidePixelGrid: (Int, Int) => Boolean = (x, y) => (x >= 0 && y >= 0 && x < width && y < height)

      for {
        (px, py) <- adjacentPixels
        if insidePixelGrid(px, py) && !seenPixels.contains((px, py))
      } yield {
        val pixelColor = new java.awt.Color(raw.getRGB(px, py))
        seenPixels.add((px, py))
        if (compareColors(currPixelColor, pixelColor)) toVisitPixels.append((px, py))
      }
    }

    propagate()
  }

  // Exporting output
  javax.imageio.ImageIO.write(raw, "jpg", new java.io.File(dest))
}