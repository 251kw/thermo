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

		String servletPath = request.getServletPath();
		// 初期アクセスの場合または
		// ログイン処理の場合または
		// グループ登録処理の場合または
		// ユーザー登録処理の場合または
		// ユーザー一括登録処理の場合
		if (TOP.equals(servletPath) ||
			LOGIN.equals(servletPath) ||
			GROUP_INFO_INP.equals(servletPath) ||
			USER_INFO_INP.equals(servletPath) ||
			USERS_MULTI_SET.equals(servletPath) ||
			SESSION_TIMEOUT.equals(servletPath)) {
			// 当条件の場合、セッションが存在しない為、タイムアウト処理は行わない
			return true;
		}
		// セッションタイムアウトの場合
		if (isSessionTimeout(request)) {
			// ログアウト処理を行う
			response.sendRedirect(request.getContextPath() + SESSION_TIMEOUT);
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
