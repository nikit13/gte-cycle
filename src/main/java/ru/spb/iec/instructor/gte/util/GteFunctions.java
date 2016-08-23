package ru.spb.iec.instructor.gte.util;

public class GteFunctions {

	private GteFunctions() {
		// do nothing
	}

	public static double calculateLc(double pi_k, double Tg, double k) {
		double Lc = Lk_calc(pi_k, 288, 1, k);
		double Lp = Lt_calc(pi_k, Tg, 1, k);
		return Lp - Lc;
	}

	public static double calculateQ1(double pi_k, double Tg, double k) {
		double Lc = Lk_calc(pi_k, 288, 1, k);
		double Tk = 288 + Lc / 1005;
		return 1005 * (Tg - Tk);
	}

	public static double calculateQ2(double pi_k, double Tg, double k) {
		double Lp = Lt_calc(pi_k, Tg, 1, k);
		double Tc = Tg - Lp / 1005;
		return 1005 * (Tc - 288);
	}

	public static double calculateKpd(double pi_k, double k) {
		return 1. - (1. / Math.pow(pi_k, (k - 1.) / k));
	}

	// '**************************************************
	// ' Функция вычисления работы компрессора
	// ' Входные параметры:
	// ' pi_k- степень повышения давления
	// ' Tv - температура воздуха на входе в компрессор
	// ' eta_k - к.п.д. компрессора
	// '
	// 'Выходные данные:Lk - работа компрессора [Дж/кг] или -1, если ошибка
	// данных
	// '****************************************************************************
	public static double Lk_calc(double pi_k, double Tv, double eta_k, double k) {
		if (k == 0) {
			k = 1.4;
		}
		if (eta_k <= 0) {
			throw new IllegalStateException("КПД не может иметь значение " + eta_k);
		} else if (pi_k < 1) {
			throw new IllegalStateException("Степень повышения давления воздуха в компрессоре не может быть меньше 1");
		} else if (Tv <= 0) {
			throw new IllegalStateException("Абсолютная температура не может быть меньше 0");
		} else {
			return 1005 * Tv * Math.pow(pi_k, (k - 1.) / k - 1.) / eta_k;
		}
	}

	//
	//
	// '**************************************************
	// ' Функция вычисления работы турбины
	// ' Входные параметры:
	// ' pi_t- степень понижения давления в турбине
	// ' Tg - температура газана входе в турбину
	// ' eta_t - к.п.д. турбины
	// '
	// 'Выходные данные:Lt - работа турбины [Дж/кг] или -1, если ошибка данных
	// '****************************************************************************
	public static double Lt_calc(double pi_t, double Tg, double eta_t, double k) {
		double c_p;
		if (k == 0) {
			k = 1.33;
			c_p = 1155;
		} else {
			k = 1.4;
			c_p = 1005;
		}
		if (eta_t <= 0) {
			throw new IllegalStateException("КПД не может иметь значение " + eta_t);
		} else if (pi_t < 1) {
			throw new IllegalStateException("Степень понижения давления газа в турбине не может быть меньше 1");
		} else if (Tg <= 0) {
			throw new IllegalStateException("Абсолютная температура не может быть меньше 0");
		} else {
			return c_p * Tg * (1. - 1. / Math.pow(pi_t, (k - 1.) / k)) * eta_t;
		}
	}
}
