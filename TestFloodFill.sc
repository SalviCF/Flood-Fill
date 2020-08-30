// For calling FloodFill function
// amm --predef FloodFill.sc TestFloodFill.sc
// mv Filled.jpg ExpectedFilled.jpg changes the name from Filled to ExpectedFilled
// diff Filled.jpg ExpectedFilled.jpg returns nothing if binary files are equals, prints a message if they differ
floodFill2(
  src = "Raw.jpg", dest = "Filled.jpg", startX = 180, startY = 90,
  compareColors = { (a: java.awt.Color, b: java.awt.Color) => // callback function: decides colors similar enough
    def sqrDiff(f: java.awt.Color => Int) = math.pow(f(a) - f(b), 2) // squared diff how much blue/red/green
    math.sqrt(sqrDiff(_.getBlue) + sqrDiff(_.getGreen) + sqrDiff(_.getRed)) < 25 // (a.blue-b.blue) + (a.green-b.green) + (a.red-b.red)
  },
  fillColor = java.awt.Color.BLACK // https://docs.oracle.com/javase/7/docs/api/java/awt/Color.html
)
/*
startX = x-coordinate of starting pixel
startY = y-coordinate of starting pixel
compareColor =  callback function to specify what color pairs are similar enough to traverse
                takes 2 colors,
fillColor = color for filling the similar enough pixels
 */