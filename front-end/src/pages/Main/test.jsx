import { useState } from 'react';
import BottomSheet from '../../components/bottomsheet/bottomsheet';
//import FilterContents from '../../components/filter/filter';

const Home = () => {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [bottomSheetTitle, setBottomSheetTitle] = useState('상세필터');

    return 
    //(
/*        <div className="wrap">
        <div className="content">
            <div className="img-box">
                <h1>~~ 테스트 진행중 ~~</h1>
            </div>
            <div className="btn-group">
                <button type="button" className="btn" onClick={() => setIsModalOpen(true)}><span>Open BottomSheet!</span></button>
            </div>
            {isModalOpen ?
             <BottomSheet title={bottomSheetTitle} closeModal={() => setIsModalOpen(false)} contents={<FilterContents/>}/> : null
            }
        </div>
    </div>*/
//    )
}

export default Home;