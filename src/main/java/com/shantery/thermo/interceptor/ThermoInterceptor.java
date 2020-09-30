package com.shantery.thermo.interceptor;

import static com.shantery.thermo.util.ThermoConstants.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author r.kawahara
 * コントローラーの前処理を行うクラス
 */
public class ThermoInterceptor extends HandlerInterceptorAdapter {

	/**
	 * セッションタイムアウト時の挙動を定義
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			                 HttpServletResponse response,
			                 Object handler) throws Exception {

		String requestUri = request.getRequestURI();
		// 初期アクセスの場合または
		// ログイン処理の場合または
		// グループ登録処理の場合または
		// ユーザー登録処理の場合または
		// ユーザー一括登録処理の場合
		if (TOP.equals(requestUri) ||
			LOGIN.equals(requestUri) ||
			TO_GROUP_INFO_INP.equals(requestUri) ||
			TO_USER_INFO_INP.equals(requestUri) ||
			USERS_MULTI_SET.equals(requestUri) ||
			SESSION_TIMEOUT.equals(requestUri)) {
			// 当条件の場合、セッションが存在しない為、タイムアウト処理は行わない
			return true;
		}

		// セッションタイムアウトの場合
		if (isSessionTimeout(request)) {
			// ログアウト処理を行う
			response.sendRedirect(SESSION_TIMEOUT);
			return false;
		}
		return true;
	}

	/**
	 * セッションタイムアウト判定
	 * @param request リクエストオブジェクト
	 * @return        タイムアウト結果
	 */
	private boolean isSessionTimeout(HttpServletRequest request) {
		HttpSession currentSession = request.getSession(false);
		// セッションが取得できない場合
		if (currentSession == null) {
			// タイムアウト
			return true;
		}
		String requestSession = request.getRequestedSessionId();
		boolean isValid = request.isRequestedSessionIdValid();
		// セッションIDが取得できない場合　または
		// セッションが無効な場合　または
		// セッションIDがリクエストセッションIDと異なる場合
		if (requestSession == null ||
			!isValid ||
			!requestSession.equals(currentSession.getId())) {
			// タイムアウト
			return true;
		}
		// 正常
		return false;
	}
}
