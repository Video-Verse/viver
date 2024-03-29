import React, { useState, useEffect } from "react";
import $ from 'jquery';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Header from "../../components/Header/Header";
import CommonInp from "../../components/input/input";
import CommonBtn from "../../components/button/button";


const Login = () => {
	var inputNickname = '';

	const [nickname, setNickname] = useState('');
	const [error, setError] = useState(null);
	const [isBtnDisabled, setIsBtnDisabled] = useState(true);
	const navigate = useNavigate();

	useEffect(() => {
		$("#title").css('display', 'none');
		$("#btn-back").css('display', 'block');
		$("#btn-search").css('visibility', 'hidden');

		if (nickname.trim() === '') {
			setIsBtnDisabled(true);
		} else {
			setIsBtnDisabled(false);
		}
	}, [nickname]);

	//input check
	const handleInputChange = (e) => {
		setError(null);
		inputNickname = e.target.value;
		setNickname(inputNickname.slice(0, 10));
	};

	const handleSubmit = (e) => {
		axios.post(
			process.env.REACT_APP_BACK_BASE_URI + '/login'
			, {
				nickname
				//,oauthAttributes : loginInfo.oauthAttributes,
			}).then(response => {
				var code = response.data.code;

				if ("000" === code) {
					var user = response.data.user;
					var userId = user.userId;
					var nickname = user.nickname;
					localStorage.setItem("userId", userId);
					localStorage.setItem("nickname", nickname);
					navigate('/home');
				} else {
					setError("가입되지않은 닉네임입니다.");
					setIsBtnDisabled(true);
				};
			});
	}

	return (
		<div>
			<Header />

			<div className="wrap">
				<div className="content">
					<h3 className="content-title mt50">
						닉네임을 입력해 주세요
					</h3>
					<CommonInp value={nickname} onChange={handleInputChange} />
					{error && <p className="err-msg">{error}</p>}
				</div>
				<CommonBtn buttonText="확인" disabled={isBtnDisabled} onClick={handleSubmit} />

			</div>
		</div>
	)
};

export default Login;