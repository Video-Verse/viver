// import logo from './assets/images/logo.png';
// import logoKr from './assets/images/logo_kr.png'
//import {useEffect, useState} from "react";

import 'slick-carousel/slick/slick-theme.css';
import 'slick-carousel/slick/slick.css';
import './styles/reset.css';

// import Header from './components/Header/Header';
import DockBar from './components/DockBar/DockBar';
import Search from './pages/Search/Search';
// import Login from './pages/Login/Login';
// import Join from './pages/Login/Join';


function App() {

    return (
    <div>
        {/* <Header /> */}
        {/* <Login /> */}
        {/* <Join /> */}
        {/* <MainSlider/> */}
        <Search />
        <DockBar />
    </div>
 
  );
}
export default App;

