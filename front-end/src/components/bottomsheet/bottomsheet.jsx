import React from "react";
import icClose from '../../assets/images/ic_close.png';
import './bottomsheet.css';

function BottomSheet ({ title, closeModal, contents }) {
    return (
      <>
        <div class="modal-background"/>
        <div class="bs">
          <div class="bs-header">
            <img src={icClose} alt="닫기" onClick={closeModal} />
          </div>
          <div class="bs-content">
            <h1 className="title">{title}</h1>
            <div>{contents}</div>
          </div>
        </div>
      </>
    );
  }
  export default BottomSheet;