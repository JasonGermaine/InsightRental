package database;

public class InitialiseTables
{
	public static void main(String[] args)
	{
		CreateUsers cu = new CreateUsers();
		cu.CreateUsersTable();
		cu.queryUser();
		System.out.println();
		cu.queryAdmin();
		System.out.println();
		cu.querySaleStaff();
		System.out.println();
		CreateMember cm = new CreateMember();
		cm.CreateMemberTable();
		cm.queryMembers();
		System.out.println();
		CreateSales cs = new CreateSales();
		cs.CreateSalesTable();
		cs.querySales();
		System.out.println();
		CreateRental cr = new CreateRental();
		cr.CreateRentalTable();
		cr.queryRental();
		System.out.println();
		CreateMovie cmov = new CreateMovie();
		cmov.CreateMovieTable();
		cmov.queryMovie();
		System.out.println();
		cmov.queryMovieCopy();
	}

}
