
The source of the jokes provided can be found in this link:

http://royal.pingdom.com/2010/06/01/17-computer-geek-jokes-and-truisms/

The source for the image displayed in the JokeActivity of the jokesdisplay library can be found here:

http://www.iconarchive.com/show/character-icons-by-martin-berube/Clown-icon.html

/////////////////////////////////////////

The backend in this project has been deployed to the Google App Engine. In order to run the gradle task runTests(), found in 
the build.gradle file in the root of this project, the local development must be run instead and in the AsyncJokeFetcher.java 
class in the doInBackground() method, the following code must be commented:


                // The backend has been deployed to the Google App Engine, using the code below
                // now to connect to the backend
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://builditbigger-1300.appspot.com/_ah/api/");


And the following code must be un commented:

                // Code used to connect to the local development server
                /*
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/") // 10.0.2.2 is localhost's IP address in Android emulator
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                        */ 

