import React, {useState} from 'react';
import i18n from './i18n';
import { Container } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import Greeting from './Components/Greeting';
import Loading from './Components/Loading';
import Navigation from './Components/Navigation';
import LocaleContext from './LocaleContext';
import Text from './Components/Text';

function App() {

    const [locale, setLocale] = useState(i18n.language);
    i18n.on('languageChanged', (lng) => setLocale(i18n.language));
  return (
      <>
          <LocaleContext.Provider value={{locale, setLocale}}>
              <React.Suspense fallback={<Loading />}>
              <Navigation />
        <Container>
          <Greeting />
          <Text />
        </Container>
              </React.Suspense>
    </LocaleContext.Provider>
      </>
  );
}

export default App;
