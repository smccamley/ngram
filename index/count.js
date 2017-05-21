var fs = require( 'fs' );
var path = require( 'path' );
var sharp = require( 'sharp' );
// In newer Node.js versions where process is already global this isn't necessary.
var process = require( "process" );
var Jimp = require( "jimp" );
var async = require('async');
var LineByLineReader = require('line-by-line');

var moveFrom = "./";
//var moveTo = "../upload"


/*
function getQuality(imageName){

  const stats = fs.statSync(imageName)
  const fileSizeInBytes = stats.size
  //Convert the file size to megabytes (optional)
  const fileSize = fileSizeInBytes / 1000.0

  var quality = 100

  if(fileSize > 250 && fileSize <= 400){
    quality = 90
  }
  if(fileSize > 400 && fileSize <= 700){
    quality = 80
  }
  if(fileSize > 700 && fileSize <= 1400){
    quality = 66
  }
  if(fileSize > 1400 && fileSize <= 2500){
    quality = 50
  }
  if(fileSize > 2500){
    quality = 40
  }
  return quality
}

// create a queue object with concurrency 2
var q = async.queue(function(task, callback) {
    
    console.log(task.index, 'processing ' + task.fromPath);

    Jimp.read(task.fromPath, function (err, image) {
        if (err) throw err
        console.log(task.fromPath, 'completed', 'q:'+ getQuality(task.fromPath))
        
          image.resize(1000, Jimp.AUTO)            // resize
             .quality(getQuality(task.fromPath))                 // set JPEG quality
             .write(
              task.toPath.substring(0, task.toPath.length-4) + '.jpg',
              function(){
                callback();
              }
             )
    });
}, 12);
*/
// assign a callback
var count = 0

var q = async.queue(function(task, callback) {
    

   console.log('**THE TOTAL IS CURRENTLY***:', count);
   console.log('processing:', task.index + " ", task.fromPath);



    var lr = new LineByLineReader(task.fromPath);
    lr.on('line', function(line){

        lineArray = line.split(" ");
        count = count + parseInt(
            lineArray[lineArray.length-1]
        );

    });

    lr.on('end', function () {
        callback();
    });

}, 1);

q.drain = function() {
    console.log('count:', count);
};



// Loop through all the files in the temp directory
fs.readdir( moveFrom, function( err, files ) {
        if( err ) {
            console.error( "Could not list the directory.", err );
            process.exit( 1 );
        } 

        files.forEach( function( file, index ) {

                if(file == 'count.js'){
                    return
                }

                // if(index>1){
                //     return;
                // }

                console.log(file)

                var fromPath = path.join( moveFrom, file );

                var fileTask = {
                    fromPath:fromPath,
                    index:index
                }

                q.push(fileTask, function(err) {
                    console.log('processing file');
                });

                

                return;

                

                //var image = sharp(fromPath);
                //image.resize(1000).toFormat('jpeg').max().toFile(toPath);

                

                //fs.stat( fromPath, function( error, stat ) {
                  //  if( error ) {
                  //      console.error( "Error stating file.", error );
                  //      return;
                  //  }

                  //  if( stat.isFile() )
                  //      console.log( "'%s' is a file.", fromPath );
                  //  else if( stat.isDirectory() )
                  //      console.log( "'%s' is a directory.", fromPath );


                    // fs.rename( fromPath, toPath, function( error ) {
                    //     if( error ) {
                    //         console.error( "File moving error.", error );
                    //     }
                    //     else {
                    //         console.log( "Moved file '%s' to '%s'.", fromPath, toPath );
                    //     }
                    // } );
                //} );
        } );
} );

